package pvt.example.project.system.dictdata.service;

import pvt.example.common.basic.Result;
import pvt.example.project.system.dictdata.domain.SysDictData;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：SysDictDataService <br/>
 * 描&emsp;&emsp;述：系统字典数据对象的Service接口层
 */
public interface SysDictDataService {
    public Result<Map<String, Object>> getIndexInfo();

    public List<SysDictData> getSysDictJson(String parentType);

    public List<SysDictData> getSysDictParent();

    public SysDictData searchDictData(SysDictData sysDictData);

    public  Boolean changeDictData(SysDictData sysDictData);

    public Boolean addDictData(SysDictData sysDictData, HttpSession httpSession);

    public Boolean deleteBatchSysDictData(String[] dictIdList);
}
