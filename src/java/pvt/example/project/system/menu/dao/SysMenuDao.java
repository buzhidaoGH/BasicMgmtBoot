package pvt.example.project.system.menu.dao;

import org.apache.ibatis.annotations.Param;
import pvt.example.project.system.menu.domain.SysMenu;

import java.util.List;

/**
 * 类&emsp;&emsp;名：SysMenuDao <br/>
 * 描&emsp;&emsp;述：系统菜单Dao层
 */
public interface SysMenuDao {
    /**
     * 递归查找所有 菜单List
     * @return 菜单数组
     */
    public List<SysMenu> findAllRecursion(@Param("menuName") String menuName);

    public SysMenu findMenuById(String menuId);

    public Boolean deleteSysMenuById(@Param("menuIdList") String[] menuIdList);

    public Boolean updateSysMenu(SysMenu sysMenu);

    public Boolean insertSysMenu(SysMenu sysMenu);
}
