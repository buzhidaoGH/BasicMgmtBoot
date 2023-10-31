package pvt.example.project.system.menu.service;

import pvt.example.project.system.menu.domain.SysMenu;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 类&emsp;&emsp;名：SysMenuService <br/>
 * 描&emsp;&emsp;述：系统菜单接口
 */
public interface SysMenuService {
    public List<SysMenu> getSysMenuJson(String menuName);

    public SysMenu getSysMenuInfo(String menuId);

    public Boolean deleteBatchSysMenu(String[] menuIdList);

    public Boolean updateSysMenu(SysMenu sysMenu, HttpSession session);

    public Boolean addSysMenu(SysMenu sysMenu, HttpSession session);
}
