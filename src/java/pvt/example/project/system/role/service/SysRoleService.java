package pvt.example.project.system.role.service;

import pvt.example.project.system.role.domain.SysRole;

import java.util.List;

/**
 * 类&emsp;&emsp;名：SysRoleService <br/>
 * 描&emsp;&emsp;述：系统角色服务层
 */
public interface SysRoleService {
    public List<SysRole> selectAllRoleList();
}
