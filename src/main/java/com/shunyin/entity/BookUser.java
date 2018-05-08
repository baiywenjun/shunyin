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
 * @since 2018-04-28
 */
@TableName("book_user")
public class BookUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账单id
     */
	@TableId(value="book_id", type= IdType.AUTO)
	private Long bookId;
    /**
     * 流水号
     */
	@TableField("serial_no")
	private String serialNo;
    /**
     * 第三方支付流水
     */
	@TableField("pay_no")
	private String payNo;
    /**
     * 操作帐号
     */
	@TableField("user_name")
	private String userName;
	@TableField("alias_user_name")
	private String aliasUserName;
    /**
     * 人民币（分）
     */
	private Integer money;
    /**
     * 美元（分）
     */
	private Integer dollar;
    /**
     * 货币单位
     */
	@TableField("monetary_unit")
	private String monetaryUnit;
    /**
     * 流转方式
     */
	@TableField("flow_way")
	private String flowWay;
    /**
     * 手续费
     */
	@TableField("take_fee")
	private Integer takeFee;
    /**
     * 汇率
     */
	private Float exchange;
    /**
     * 余额
     */
	private Integer balance;
	@TableField("create_time")
	private Date createTime;
    /**
     * 状态，转账状态
     */
	private String status;
	private String remark;
    /**
     * 转入出类型：0入金，1出金。必填
     */
	private Integer type;


	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAliasUserName() {
		return aliasUserName;
	}

	public void setAliasUserName(String aliasUserName) {
		this.aliasUserName = aliasUserName;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getDollar() {
		return dollar;
	}

	public void setDollar(Integer dollar) {
		this.dollar = dollar;
	}

	public String getMonetaryUnit() {
		return monetaryUnit;
	}

	public void setMonetaryUnit(String monetaryUnit) {
		this.monetaryUnit = monetaryUnit;
	}

	public String getFlowWay() {
		return flowWay;
	}

	public void setFlowWay(String flowWay) {
		this.flowWay = flowWay;
	}

	public Integer getTakeFee() {
		return takeFee;
	}

	public void setTakeFee(Integer takeFee) {
		this.takeFee = takeFee;
	}

	public Float getExchange() {
		return exchange;
	}

	public void setExchange(Float exchange) {
		this.exchange = exchange;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "BookUser{" +
			"bookId=" + bookId +
			", serialNo=" + serialNo +
			", payNo=" + payNo +
			", userName=" + userName +
			", aliasUserName=" + aliasUserName +
			", money=" + money +
			", dollar=" + dollar +
			", monetaryUnit=" + monetaryUnit +
			", flowWay=" + flowWay +
			", takeFee=" + takeFee +
			", exchange=" + exchange +
			", balance=" + balance +
			", createTime=" + createTime +
			", status=" + status +
			", remark=" + remark +
			", type=" + type +
			"}";
	}
}
