package pvt.example.project.system.role.domain;

import pvt.example.common.basic.BaseEntity;

import java.util.Arrays;
import java.util.Set;

/**
 * 类&emsp;&emsp;名：SysRole <br/>
 * 描&emsp;&emsp;述：角色表 sys_role
 */
public class SysRole extends BaseEntity {
    /** 角色ID */
    private Long roleId;
    /** 角色名称 */
    private String roleName;
    /** 角色权限 */
    private String roleKey;
    /** 数据范围（1：所有数据权限；2：自定义数据权限；） */
    private String dataScope;
    /** 角色状态（0正常 1停用） */
    private String status;
    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;
    /** 菜单组 */
    private Long[] menuIds;
    /** 角色菜单权限 */
    private Set<String> permissions;

    public SysRole() {}

    public SysRole(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Long[] getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(Long[] menuIds) {
        this.menuIds = menuIds;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "SysRole{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleKey='" + roleKey + '\'' +
                ", dataScope='" + dataScope + '\'' +
                ", status='" + status + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", menuIds=" + Arrays.toString(menuIds) +
                ", permissions=" + permissions +
                "} " + super.toString();
    }
}
