package pvt.example.project.system.role.dao;

import pvt.example.project.system.role.domain.SysRole;

import java.util.List;

/**
 * 类&emsp;&emsp;名：SysRoleDao <br/>
 * 描&emsp;&emsp;述：系统角色Dao层
 */
public interface SysRoleDao {
    public List<SysRole> selectRoleList(SysRole sysRole);
}
