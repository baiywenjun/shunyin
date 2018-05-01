package com.shunyin.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * @since 2018-04-25
 */
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="account_id", type= IdType.AUTO)
	private Long accountId;
	@TableField("user_name")
	private String userName;
	private String password;
	private String name;
	private String sex;
	private Date birthday;
	private String phone;
	private String status;
	@TableField("create_time")
	private Date createTime;
	@TableField("modify_time")
	private Date modifyTime;
    /**
     * 帐号映射别名
     */
	@TableField("alias_account")
	private String aliasAccount;
    /**
     * 身份证号码
     */
	private String identity;


	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getAliasAccount() {
		return aliasAccount;
	}

	public void setAliasAccount(String aliasAccount) {
		this.aliasAccount = aliasAccount;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	@Override
	public String toString() {
		return "SysUser{" +
			"accountId=" + accountId +
			", userName=" + userName +
			", password=" + password +
			", name=" + name +
			", sex=" + sex +
			", birthday=" + birthday +
			", phone=" + phone +
			", status=" + status +
			", createTime=" + createTime +
			", modifyTime=" + modifyTime +
			", aliasAccount=" + aliasAccount +
			", identity=" + identity +
			"}";
	}
}
