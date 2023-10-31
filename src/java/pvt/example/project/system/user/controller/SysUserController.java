package pvt.example.project.system.user.controller;

import com.wf.captcha.utils.CaptchaUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pvt.example.common.basic.Result;
import pvt.example.framework.listener.MyHttpSessionListener;
import pvt.example.project.system.role.domain.SysRole;
import pvt.example.project.system.user.domain.SysUser;
import pvt.example.project.system.user.service.SysUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 类&emsp;&emsp;名：SysUserController <br/>
 * 描&emsp;&emsp;述：用户对象的Controller层
 */
@Controller
@RequestMapping("/sys_user")
public class SysUserController {
    private final String prefix = "system/user";
    @Resource
    private SysUserService sysUserService;

    /**
     * user页面跳转
     */
    @GetMapping("/user")
    public String user() {return prefix + "/user";}

    /**
     * user_add页面跳转
     */
    @GetMapping("/user_add")
    public String userAdd() {return prefix + "/user_add";}

    /**
     * user_edit页面跳转
     */
    @GetMapping("/user_edit")
    public String userEdit() {return prefix + "/user_edit";}

    /**
     * user_details页面跳转
     */
    @GetMapping("/user_details")
    public String userDetails() {return prefix + "/user_details";}

    /**
     * 登陆逻辑
     */
    @PostMapping("/login")
    @ResponseBody
    public Result<String> login(SysUser sysUser,
                                @RequestParam("captcha") String captcha,
                                HttpServletRequest request,
                                HttpSession session) {
        if (!CaptchaUtil.ver(captcha, request)) {
            return new Result<>(-1, "验证码错误！");
        }
        CaptchaUtil.clear(request);
        SysUser loginUser = sysUserService.login(sysUser);
        if (loginUser != null) {
            // 登录成功
            if (loginUser.getDelFlag().equals("1")) {return new Result<>(-1, "此用户已注销！");}
            if (loginUser.getStatus().equals("1")) {return new Result<>(-1, "此用户已禁用！");}
            session.setAttribute("userInfo", loginUser);
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            Map<String, String> loginInfo = new HashMap<>();
            loginInfo.put("browser", userAgent.getBrowser().getName());
            loginInfo.put("system", userAgent.getOperatingSystem().getName());
            session.setAttribute("loginInfo", loginInfo);
            sysUserService.loginSuccess(loginUser, request);
            return new Result<>(0, "登陆成功！");
        }
        return new Result<>(-1, "用户名或密码错误！");
    }

    @PostMapping("/logout")
    @ResponseBody
    public Result<String> logout(SysUser sysUser, HttpSession session) {
        SysUser userInfo = (SysUser) session.getAttribute("userInfo");
        if (userInfo.getUserName().equals(sysUser.getUserName()) &&
                userInfo.getUserId().equals(sysUser.getUserId())) {
            session.removeAttribute("userInfo");
            session.invalidate();
            return new Result<>(0, "成功退出！");
        }
        return new Result<>(-1, "退出失败！");
    }

    @PostMapping("/change_pwd")
    @ResponseBody
    public Result<String> changePwd(String userName, String newPwd, String oldPwd, HttpSession session) {
        Boolean changeFlag = sysUserService.changePwd(userName, newPwd, oldPwd, session);
        if (changeFlag) {
            session.removeAttribute("userInfo");
            session.invalidate();
        }
        return changeFlag ? new Result<>(0, "修改成功！") : new Result<>(-1, "修改失败！");
    }

    @RequestMapping("/user_online")
    @ResponseBody
    public Result<List<Map<String, Object>>> userOnline() {
        Map<String, HttpSession> sessionOnlineMap = MyHttpSessionListener.sessionOnlineMap;
        List<Map<String, Object>> sysUserInfoList = new ArrayList<>();
        Set<String> onlineMapKeys = sessionOnlineMap.keySet();
        for (String onlineMapKey : onlineMapKeys) {
            HttpSession httpSession = sessionOnlineMap.get(onlineMapKey);
            SysUser userInfo = (SysUser) httpSession.getAttribute("userInfo");
            Map<String, Object> loginInfo = (Map<String, Object>) httpSession.getAttribute("loginInfo");
            Field[] declaredFields = userInfo.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                try {
                    loginInfo.put(field.getName(), field.get(userInfo));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            loginInfo.put("sessionId", httpSession.getId());
            sysUserInfoList.add(loginInfo);
        }
        return new Result<>(sysUserInfoList);
    }

    /**
     * 获取当前登陆用户info
     */
    @RequestMapping("/user_info")
    @ResponseBody
    public Result<SysUser> userInfo(HttpSession session) {
        SysUser userInfo = (SysUser) session.getAttribute("userInfo");
        return new Result<>(userInfo);
    }

    /**
     * 获取用户列表(可条件查询)
     */
    @PostMapping("/user_list")
    @ResponseBody
    public Result<List<SysUser>> list(SysUser sysUser) {
        return new Result<>(sysUserService.selectUserList(sysUser));
    }

    /**
     * 删除用户(可批量数组删除)
     */
    @PostMapping("/delete_user")
    @ResponseBody
    public Result<String> deleteBatchSysUser(@RequestBody Integer[] ids) {
        return sysUserService.deleteBatchSysUser(ids) ?
               new Result<>(0, "删除成功") :
               new Result<>(-1, "删除失败");
    }

    /**
     * 用户使用状态改变
     */
    @PostMapping("/change_user_status")
    @ResponseBody
    public Result<String> changeUserStatus(@RequestBody SysUser sysUser) {
        Boolean updateFlag = sysUserService.updateUserStatus(sysUser);
        if (updateFlag) {
            return new Result<>(0, "更改成功！");
        } else {
            return new Result<>(-1, "更改失败！");
        }
    }

    /**
     * 增加用户(或者头像文件)
     */
    @PostMapping("/add_sys_user")
    @ResponseBody
    public Result<String> addSysUser(
            SysUser sysUser, SysRole sysRole, HttpSession session,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile) {
        SysUser nowUser = (SysUser) session.getAttribute("userInfo");
        Long newUserId = sysUserService.addSysUser(sysUser, nowUser, sysRole, avatarFile);
        return newUserId != null ? new Result<>(0, "用户新建成功！") : new Result<>(-1, "用户新建失败！");
    }

    /**
     * 编辑用户(或者头像文件)
     */
    @PostMapping("/update_sys_user")
    @ResponseBody
    public Result<String> updateSysUser(
            SysUser sysUser, SysRole sysRole, HttpSession session,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile) {
        SysUser nowUser = (SysUser) session.getAttribute("userInfo");
        Boolean updateFlag = sysUserService.updateUserStatus(sysUser, nowUser, sysRole, avatarFile);
        if (updateFlag) { return new Result<>(0, "数据更新成功！");}
        return new Result<>(-1, "数据更新失败！");
    }

    /**
     * 随机密码(PS需要限定Referer必需为:/sys_user/user_edit/)
     * 必需传入userId
     */
    @PostMapping("/random_password")
    @ResponseBody
    public Result<String> randomPassword(
            @RequestParam String userId
            , HttpServletRequest httpServletRequest) {
        String referer = httpServletRequest.getHeader("referer");
        if (referer.contains("/sys_user/user_edit/")) {
            String randomPassword = sysUserService.randomPasswordById(userId);
            return randomPassword != null ?
                   new Result<>(randomPassword) :
                   new Result<>(-1, "重置密码失败！");
        }
        return new Result<>(-1, "请求失败！");
    }
}
