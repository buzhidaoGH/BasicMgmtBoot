package pvt.example.project.system.role.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pvt.example.common.basic.Result;
import pvt.example.project.system.role.domain.SysRole;
import pvt.example.project.system.role.service.SysRoleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类&emsp;&emsp;名：SysRoleController <br/>
 * 描&emsp;&emsp;述：系统角色控制接口层
 */
@Controller
@RequestMapping("/sys_role")
public class SysRoleController {
    private final String prefix = "system/role";
    @Resource
    private SysRoleService sysRoleService;

    /**
     * role页面跳转
     */
    @GetMapping("/role")
    public String role() {return prefix + "/role";}

    /**
     * role_details页面跳转
     */
    @GetMapping("/role_details")
    public String roleDetails() {return prefix + "/role_details";}

    @PostMapping("/role_list")
    @ResponseBody
    public Result<List<SysRole>> roleList() {
        List<SysRole> sysRoleList = sysRoleService.selectAllRoleList();
        return new Result<>(sysRoleList);
    }
}
