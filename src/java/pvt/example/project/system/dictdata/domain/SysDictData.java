package pvt.example.project.system.dictdata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * 类&emsp;&emsp;名：SysDictData <br/>
 * 描&emsp;&emsp;述：字典数据表 sys_dict_data
 */
public class SysDictData {
    /** 字典编码 */
    private Long dictId;
    /**
     * 父ID编号
     */
    private Long parentId;
    /** 字典排序 */
    private Long dictSort;
    /** 字典标签 */
    private String dictLabel;
    /** 字典类型 */
    private String dictType;
    /** 字典键值 */
    private String dictValue;
    /** 创建用户 */
    private String createBy;
    /** 图标字符串 */
    private String icon;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /** 备注 */
    private String remark;
    /** 子字典数据 */
    private List<SysDictData> children;

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public Long getDictSort() {
        return dictSort;
    }

    public void setDictSort(Long dictSort) {
        this.dictSort = dictSort;
    }

    public String getDictLabel() {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel) {
        this.dictLabel = dictLabel;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<SysDictData> getChildren() {
        return children;
    }

    public void setChildren(List<SysDictData> children) {
        this.children = children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "SysDictData{" +
                "dictId=" + dictId +
                ", parentId=" + parentId +
                ", dictSort=" + dictSort +
                ", dictLabel='" + dictLabel + '\'' +
                ", dictType='" + dictType + '\'' +
                ", dictValue='" + dictValue + '\'' +
                ", createBy='" + createBy + '\'' +
                ", icon='" + icon + '\'' +
                ", createTime=" + createTime +
                ", remark='" + remark + '\'' +
                ", children=" + children +
                '}';
    }
}
