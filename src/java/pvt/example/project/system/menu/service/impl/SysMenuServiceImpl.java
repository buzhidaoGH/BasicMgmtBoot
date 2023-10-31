package pvt.example.project.system.menu.service.impl;

import org.springframework.stereotype.Service;
import pvt.example.project.system.menu.dao.SysMenuDao;
import pvt.example.project.system.menu.domain.SysMenu;
import pvt.example.project.system.menu.service.SysMenuService;
import pvt.example.project.system.user.domain.SysUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 类&emsp;&emsp;名：SysMenuServiceImpl <br/>
 * 描&emsp;&emsp;述：系统菜单接口实现类
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuDao sysMenuDao;

    @Override
    public List<SysMenu> getSysMenuJson(String menuName) {
        return sysMenuDao.findAllRecursion(menuName);
    }

    @Override
    public SysMenu getSysMenuInfo(String menuId) {
        return sysMenuDao.findMenuById(menuId);
    }

    @Override
    public Boolean deleteBatchSysMenu(String[] menuIdList) {
        return sysMenuDao.deleteSysMenuById(menuIdList);
    }

    @Override
    public Boolean updateSysMenu(SysMenu sysMenu, HttpSession session) {
        Date nowDate = new Date();
        SysUser userInfo = (SysUser) session.getAttribute("userInfo");
        sysMenu.setUpdateBy(userInfo.getUserName());
        sysMenu.setUpdateTime(nowDate);
        return sysMenuDao.updateSysMenu(sysMenu);
    }

    @Override
    public Boolean addSysMenu(SysMenu sysMenu, HttpSession session) {
        Date nowDate = new Date();
        SysUser userInfo = (SysUser) session.getAttribute("userInfo");
        sysMenu.setUpdateBy(userInfo.getUserName());
        sysMenu.setUpdateTime(nowDate);
        sysMenu.setCreateBy(userInfo.getUserName());
        sysMenu.setCreateTime(nowDate);
        return sysMenuDao.insertSysMenu(sysMenu);
    }
}
