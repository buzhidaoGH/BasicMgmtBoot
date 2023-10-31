package pvt.example.project.system.user.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pvt.example.common.utils.FileUtil;
import pvt.example.common.utils.IPUtil;
import pvt.example.common.utils.ShaUtil;
import pvt.example.common.utils.StringGeneratorUtil;
import pvt.example.project.system.role.domain.SysRole;
import pvt.example.project.system.user.dao.SysUserDao;
import pvt.example.project.system.user.domain.SysUser;
import pvt.example.project.system.user.service.SysUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 类&emsp;&emsp;名：SysUserServiceImpl <br/>
 * 描&emsp;&emsp;述：用户对象Service逻辑实现层
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    @Override
    public SysUser login(SysUser sysUser) {
        sysUser.setPassword(ShaUtil.getSha1(sysUser.getPassword()));
        SysUser loginUser = sysUserDao.getSysUser(sysUser);
        if (loginUser != null) {
            loginUser.setPassword(null); //清除用户密码
        }
        return loginUser;
    }

    @Override
    public void loginSuccess(SysUser sysUser, HttpServletRequest request) {
        String loginIp = IPUtil.getIpAddr(request);
        Date loginDate = new Date();
        sysUser.setLoginIp(loginIp);
        sysUser.setLoginDate(loginDate);
        sysUserDao.uploadSysUserLoginById(sysUser);
    }

    @Override
    public List<SysUser> selectUserList(SysUser sysUser) {
        return sysUserDao.selectUserList(sysUser);
    }

    @Override
    public Boolean deleteBatchSysUser(Integer[] ids) {
        return sysUserDao.deleteSysUserById(ids);
    }

    @Override
    public Boolean updateUserStatus(SysUser sysUser) {
        return sysUserDao.updateUserStatusById(sysUser);
    }

    @Override
    public Boolean updateUserStatus(SysUser sysUser, SysUser nowUser, SysRole sysRole, MultipartFile avatarFile) {
        // 头像地址与存储头像文件
        if (avatarFile != null) {
            String avatarStr = sysUser.getAvatar();
            if (avatarStr != null
                    && !"/assets/images/default-avatar-male.png".equalsIgnoreCase(avatarStr)
                    && !"/assets/images/default-avatar-female.png".equalsIgnoreCase(avatarStr)) {
                FileUtil.deleteFile(avatarStr);
            }
            String filePathName = FileUtil.uploadFile(avatarFile, "image");
            sysUser.setAvatar(filePathName);
        }
        Date nowDate = new Date();
        sysUser.setUpdateBy(nowUser.getUserName());
        sysUser.setUpdateTime(nowDate);
        return sysUserDao.updateSysUser(sysUser)
                && sysUserDao.updateSysUserRole(sysUser.getUserId(), sysRole.getRoleId());
    }

    @Override
    public Long addSysUser(SysUser sysUser, SysUser nowUser, SysRole sysRole, MultipartFile avatarFile) {
        // 头像地址与存储头像文件
        if (avatarFile != null) {
            String filePathName = FileUtil.uploadFile(avatarFile, "image");
            sysUser.setAvatar(filePathName);
        } else {
            sysUser.setAvatar(sysUser.getGender().equals("0") ?
                              "/assets/images/default-avatar-male.png" :
                              "/assets/images/default-avatar-female.png");
        }
        Date nowDate = new Date();
        sysUser.setPassword(ShaUtil.getSha1(sysUser.getPassword()));
        sysUser.setCreateBy(nowUser.getUserName());
        sysUser.setUpdateBy(nowUser.getUserName());
        sysUser.setCreateTime(nowDate);
        sysUser.setUpdateTime(nowDate);
        boolean insertFlag = sysUserDao.insertSysUser(sysUser) &&
                sysUserDao.insertSysUserRole(sysUser.getUserId(), sysRole.getRoleId());
        return insertFlag ? sysUser.getUserId() : null;
    }

    @Override
    public String randomPasswordById(String userId) {
        String password = StringGeneratorUtil.getRandomPwd(8);
        Boolean flag = sysUserDao.changePasswordById(userId, ShaUtil.getSha1(password));
        return flag ? password : null;
    }

    @Override
    public Boolean changePwd(String userName, String newPwd, String oldPwd, HttpSession session) {
        SysUser userInfo = (SysUser) session.getAttribute("userInfo");
        if (!userInfo.getUserName().equals(userName)) {return false;}
        userInfo.setPassword(ShaUtil.getSha1(oldPwd));
        SysUser sysUser = sysUserDao.getSysUser(userInfo);
        System.out.println("sysUser = " + sysUser);
        if (null != sysUser) {
            return sysUserDao.changePasswordById(
                    sysUser.getUserId() + "",
                    ShaUtil.getSha1(newPwd));
        }
        return false;
    }
}
