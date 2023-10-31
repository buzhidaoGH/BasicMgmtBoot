package pvt.example.project.system.user.dao;

import org.apache.ibatis.annotations.Param;
import pvt.example.project.system.user.domain.SysUser;

import java.util.List;

/**
 * 类&emsp;&emsp;名：SysUserDao <br/>
 * 描&emsp;&emsp;述：用户对象的DAO层
 */
public interface SysUserDao {
    public SysUser getSysUser(SysUser sysUser);

    public void uploadSysUserLoginById(SysUser sysUser);

    public List<SysUser> selectUserList(SysUser sysUser);

    public Boolean deleteSysUserById(@Param("ids") Integer[] ids);

    public Boolean updateUserStatusById(SysUser sysUser);

    public Boolean insertSysUser(SysUser sysUser);

    public Boolean insertSysUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    public Boolean updateSysUser(SysUser sysUser);

    public Boolean updateSysUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    public Boolean changePasswordById(@Param("userId") String userId, @Param("password") String password);
}
