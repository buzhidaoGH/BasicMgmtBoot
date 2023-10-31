package pvt.example.project.system.dictdata.service.impl;

import org.springframework.stereotype.Service;
import pvt.example.common.basic.Result;
import pvt.example.project.system.dictdata.dao.SysDictDataDao;
import pvt.example.project.system.dictdata.domain.SysDictData;
import pvt.example.project.system.dictdata.service.SysDictDataService;
import pvt.example.project.system.user.domain.SysUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：SysDictDataServiceImpl <br/>
 * 描&emsp;&emsp;述：系统字典数据对象的Service接口实现层
 */
@Service
public class SysDictDataServiceImpl implements SysDictDataService {
    @Resource
    private SysDictDataDao sysDictDataDao;

    @Override
    public Result<Map<String, Object>> getIndexInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("logoTitle", sysDictDataDao.getSysDictDataByType("logo_title"));
        map.put("sysTitle", sysDictDataDao.getSysDictDataByType("sys_title"));
        return new Result<>(map);
    }

    @Override
    public List<SysDictData> getSysDictJson(String parentType) {
        return sysDictDataDao.findAllRecursion(parentType);
    }

    @Override
    public List<SysDictData> getSysDictParent() {
        return sysDictDataDao.findSysDictByParent();
    }

    @Override
    public SysDictData searchDictData(SysDictData sysDictData) {
        return sysDictDataDao.findSysDictData(sysDictData);
    }

    @Override
    public Boolean changeDictData(SysDictData sysDictData) {
        return sysDictDataDao.updateSysDictData(sysDictData);
    }

    @Override
    public Boolean addDictData(SysDictData sysDictData, HttpSession session) {
        Date nowDate = new Date();
        SysUser nowUser = (SysUser) session.getAttribute("userInfo");
        sysDictData.setCreateTime(nowDate);
        sysDictData.setCreateBy(nowUser.getUserName());
        System.out.println("sysDictData = " + sysDictData);
        return sysDictDataDao.addSysDictData(sysDictData);
    }

    @Override
    public Boolean deleteBatchSysDictData(String[] dictIdList) {
        return sysDictDataDao.deleteSysDictDataById(dictIdList);
    }
}
