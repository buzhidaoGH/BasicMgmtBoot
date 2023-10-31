package pvt.example.project.system.dictdata.dao;

import org.apache.ibatis.annotations.Param;
import pvt.example.project.system.dictdata.domain.SysDictData;

import java.util.List;

/**
 * 类&emsp;&emsp;名：SysDictDataDao <br/>
 * 描&emsp;&emsp;述：系统字典数据DAO层
 */
public interface SysDictDataDao {
    public SysDictData getSysDictDataByType(String type);

    public List<SysDictData> findAllRecursion(@Param("parentType") String parentType);

    public List<SysDictData> findSysDictByParent();

    public SysDictData findSysDictData(SysDictData sysDictData);

    public Boolean updateSysDictData(SysDictData sysDictData);

    public Boolean addSysDictData(SysDictData sysDictData);

    public Boolean deleteSysDictDataById(@Param("dictIdList") String[] dictIdList);
}
