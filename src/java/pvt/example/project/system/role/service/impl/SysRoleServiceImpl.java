package pvt.example.project.system.role.service.impl;

import org.springframework.stereotype.Service;
import pvt.example.project.system.role.dao.SysRoleDao;
import pvt.example.project.system.role.domain.SysRole;
import pvt.example.project.system.role.service.SysRoleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类&emsp;&emsp;名：SysRoleServiceImpl <br/>
 * 描&emsp;&emsp;述：
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public List<SysRole> selectAllRoleList() {
        return sysRoleDao.selectRoleList(null);
    }
}
