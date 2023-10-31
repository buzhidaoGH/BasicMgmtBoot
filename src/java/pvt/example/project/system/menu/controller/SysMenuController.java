package pvt.example.project.system.menu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pvt.example.common.basic.Result;
import pvt.example.project.system.menu.domain.SysMenu;
import pvt.example.project.system.menu.service.SysMenuService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 类&emsp;&emsp;名：SysMenuController <br/>
 * 描&emsp;&emsp;述：系统菜单表Controller
 */
@Controller
@RequestMapping("/sys_menu")
public class SysMenuController {
    private final String prefix = "system/menu";
    @Resource
    private SysMenuService sysMenuService;

    @RequestMapping("/menu")
    public String menu() {return prefix + "/menu";}

    @RequestMapping("/menu_add")
    public String addMenu() {return prefix + "/menu_add";}

    @RequestMapping("/menu_edit")
    public String editMenu() {return prefix + "/menu_edit";}

    @RequestMapping("/menu_tree")
    public String treeMenu() {return prefix + "/menu_tree";}

    /**
     * 获取菜单list信息
     */
    @ResponseBody
    @PostMapping("/menu_list")
    public Result<List<SysMenu>> getMenuList(String menuName) {
        return new Result<>(sysMenuService.getSysMenuJson(menuName));
    }

    /**
     * 通过menuId获取此菜单的详细信息
     */
    @ResponseBody
    @PostMapping("/menu_info")
    public Result<SysMenu> getMenuInfo(String menuId) {
        return new Result<>(sysMenuService.getSysMenuInfo(menuId));
    }

    /**
     * 菜单删除 by menuId
     */
    @PostMapping("/delete_menu")
    @ResponseBody
    public Result<String> deleteMenuData(@RequestParam("menuIdList[]") String[] menuIdList) {
        return sysMenuService.deleteBatchSysMenu(menuIdList) ?
               new Result<>("删除成功！") :
               new Result<>("删除失败！");
    }

    /**
     * 菜单更新
     */
    @PostMapping("/update_menu")
    @ResponseBody
    public Result<String> updateMenu(SysMenu sysMenu, HttpSession session) {
        return sysMenuService.updateSysMenu(sysMenu,session) ?
               new Result<>("更新成功！") : new Result<>("更新失败！");
    }

    /**
     * 菜单增加
     */
    @PostMapping("/add_menu")
    @ResponseBody
    public Result<String> addMenu(SysMenu sysMenu, HttpSession session) {
        return sysMenuService.addSysMenu(sysMenu,session) ?
               new Result<>("增加成功！") : new Result<>("增加失败！");
    }
}
