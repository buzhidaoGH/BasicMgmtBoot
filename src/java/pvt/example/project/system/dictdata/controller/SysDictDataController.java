package pvt.example.project.system.dictdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pvt.example.common.basic.Result;
import pvt.example.project.system.dictdata.domain.SysDictData;
import pvt.example.project.system.dictdata.service.SysDictDataService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：SysDictDataController <br/>
 * 描&emsp;&emsp;述：系统字典数据对象的Controller层
 */
@Controller
@RequestMapping("/sys_dict_data")
public class SysDictDataController {
    private final String prefix = "system/dict";
    @Resource
    private SysDictDataService sysDictDataService;

    /**
     * dict页面跳转
     */
    @GetMapping("/dict")
    public String dict() {return prefix + "/dict";}

    /**
     * dict_edit页面跳转
     */
    @GetMapping("/dict_edit")
    public String dictEdit() {return prefix + "/dict_edit";}

    /**
     * dict_add页面跳转
     */
    @GetMapping("/dict_add")
    public String dictAdd() {return prefix + "/dict_add";}

    /**
     * 简单的字典数据
     */
    @RequestMapping("/index_info")
    @ResponseBody
    public Result<Map<String, Object>> userInfo() {
        return sysDictDataService.getIndexInfo();
    }

    /**
     * 所有根字典数据
     */
    @PostMapping("/root_dict")
    @ResponseBody
    public Result<List<SysDictData>> getDictParent() {
        return new Result<>(sysDictDataService.getSysDictParent());
    }

    /**
     * 字典数据列表
     */
    @PostMapping("/dict_list")
    @ResponseBody
    public Result<List<SysDictData>> getDictList(
            @RequestParam(name = "parentType", required = false) String parentType) {
        return new Result<>(sysDictDataService.getSysDictJson(parentType));
    }

    /**
     * 字典查询
     */
    @PostMapping("/dict_data")
    @ResponseBody
    public Result<SysDictData> searchDictData(SysDictData sysDictData) {
        return new Result<>(sysDictDataService.searchDictData(sysDictData));
    }

    /**
     * 字典数据更新
     */
    @PostMapping("/update_dict_data")
    @ResponseBody
    public Result<String> changeDictData(SysDictData sysDictData) {
        Boolean flag = sysDictDataService.changeDictData(sysDictData);
        if (flag) {return new Result<>("更新成功！");}
        return new Result<>("更新失败！");
    }

    /**
     * 字典数据增加
     */
    @PostMapping("/add_dict_data")
    @ResponseBody
    public Result<String> addDictData(SysDictData sysDictData, HttpSession session) {
        Boolean flag = sysDictDataService.addDictData(sysDictData, session);
        if (flag) {return new Result<>("添加成功！");}
        return new Result<>("添加失败！");
    }

    /**
     * 字典数据删除
     */
    @PostMapping("/delete_dict_data")
    @ResponseBody
    public Result<String> deleteDictData(@RequestParam("dictIdList[]") String[] dictIdList) {
        return sysDictDataService.deleteBatchSysDictData(dictIdList) ?
               new Result<>("删除成功！") :
               new Result<>("删除失败！");
    }
}
