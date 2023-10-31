package pvt.example.project.system.user.service;

import org.springframework.web.multipart.MultipartFile;
import pvt.example.project.system.role.domain.SysRole;
import pvt.example.project.system.user.domain.SysUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 类&emsp;&emsp;名：SysUserService <br/>
 * 描&emsp;&emsp;述：用户对象的Service接口层
 */
public interface SysUserService {
    /**
     * 通过用户中的账号密码判断此用户是否存在
     * @param sysUser
     * @return 处理后的账号密码
     */
    public SysUser login(SysUser sysUser);

    public void loginSuccess(SysUser sysUser, HttpServletRequest request);

    public List<SysUser> selectUserList(SysUser sysUser);

    public Boolean deleteBatchSysUser(Integer[] ids);

    public Boolean updateUserStatus(SysUser sysUser);

    public Boolean updateUserStatus(SysUser sysUser, SysUser nowUser, SysRole sysRole, MultipartFile avatarFile);

    public Long addSysUser(SysUser sysUser, SysUser nowUser, SysRole sysRole, MultipartFile avatarFile);

    public String randomPasswordById(String userId);

    public Boolean changePwd(String userName, String newPwd, String oldPwd, HttpSession session);
}
