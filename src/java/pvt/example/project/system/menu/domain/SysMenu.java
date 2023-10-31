package pvt.example.project.system.menu.domain;

import pvt.example.common.basic.BaseEntity;

import java.util.List;

/**
 * 类&emsp;&emsp;名：SysMenu <br/>
 * 描&emsp;&emsp;述：菜单权限表 sys_menu
 */
public class SysMenu extends BaseEntity {
    /** 菜单ID */
    private Long menuId;
    /** 菜单名称 */
    private String menuName;
    /** 父菜单ID */
    private Long parentId;
    /** 父菜单名称 */
    private String parentName;
    /** 显示顺序 */
    private Integer orderNum;
    /** 菜单URL|path */
    private String path;
    /** 是否为外链（0是 1否） */
    private String isFrame;
    /** 类型（M目录 C菜单 F按钮） */
    private String menuType;
    /** 菜单状态（0显示 1隐藏） */
    private String visible;
    /** 权限字符串标识 */
    private String perms;
    /** 菜单图标 */
    private String icon;
    /** 备注 */
    private String remark;
    /** 子菜单 */
    private List<SysMenu> children;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIsFrame() {
        return isFrame;
    }

    public void setIsFrame(String isFrame) {
        this.isFrame = isFrame;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
                "menuId=" + menuId +
                ", menuName='" + menuName + '\'' +
                ", parentId=" + parentId +
                ", parentName='" + parentName + '\'' +
                ", orderNum=" + orderNum +
                ", path='" + path + '\'' +
                ", isFrame='" + isFrame + '\'' +
                ", menuType='" + menuType + '\'' +
                ", visible='" + visible + '\'' +
                ", perms='" + perms + '\'' +
                ", icon='" + icon + '\'' +
                ", remark='" + remark + '\'' +
                ", children=" + children +
                "} " + super.toString();
    }
}
