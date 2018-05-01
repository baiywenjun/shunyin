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
 * @since 2018-04-27
 */
@TableName("book_admin")
public class BookAdmin implements Serializable {

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
     * 入金（分）
     */
	private Integer income;
    /**
     * 出金（分）
     */
	private Integer out;
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
	private Integer deposit;
	@TableField("create_time")
	private Date createTime;
    /**
     * 状态，转账状态
     */
	private String status;
	private String remark;


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

	public Integer getIncome() {
		return income;
	}

	public void setIncome(Integer income) {
		this.income = income;
	}

	public Integer getOut() {
		return out;
	}

	public void setOut(Integer out) {
		this.out = out;
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

	public Integer getDeposit() {
		return deposit;
	}

	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
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

	@Override
	public String toString() {
		return "BookAdmin{" +
			"bookId=" + bookId +
			", serialNo=" + serialNo +
			", income=" + income +
			", out=" + out +
			", monetaryUnit=" + monetaryUnit +
			", flowWay=" + flowWay +
			", takeFee=" + takeFee +
			", exchange=" + exchange +
			", deposit=" + deposit +
			", createTime=" + createTime +
			", status=" + status +
			", remark=" + remark +
			"}";
	}
}
