package com.shunyin.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wenjun
 * @since 2018-04-27
 */
@TableName("sys_dict")
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="dict_id", type= IdType.AUTO)
	private Long dictId;
	@TableField("dict_name")
	private String dictName;
	@TableField("dict_code")
	private String dictCode;
	@TableField("dict_value")
	private String dictValue;
	@TableField("parent_id")
	private Long parentId;
	private Integer sort;
	private String remark;


	public Long getDictId() {
		return dictId;
	}

	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getremark() {
		return remark;
	}

	public void setremark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "SysDict{" +
			"dictId=" + dictId +
			", dictName=" + dictName +
			", dictCode=" + dictCode +
			", dictValue=" + dictValue +
			", parentId=" + parentId +
			", sort=" + sort +
			", remark=" + remark +
			"}";
	}
}
