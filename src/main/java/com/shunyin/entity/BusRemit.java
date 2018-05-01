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
@TableName("bus_remit")
public class BusRemit implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="remit_id", type= IdType.AUTO)
	private Long remitId;
    /**
     * 系统账号
     */
	@TableField("user_name")
	private String userName;
    /**
     * 接口帐号
     */
	@TableField("alias_user_name")
	private String aliasUserName;
    /**
     * 汇款人名字
     */
	@TableField("remit_real_name")
	private String remitRealName;
    /**
     * 汇款银行卡号
     */
	@TableField("remit_bank_card")
	private String remitBankCard;
    /**
     * 汇款银行名字
     */
	@TableField("remit_bank_name")
	private String remitBankName;
    /**
     * 汇款人民币（分）
     */
	@TableField("remit_money")
	private Integer remitMoney;
    /**
     * 汇款美元（分）
     */
	@TableField("remit_dollar")
	private Integer remitDollar;
    /**
     * 汇率
     */
	private Float exchange;
    /**
     * 收款人姓名
     */
	@TableField("to_real_name")
	private String toRealName;
    /**
     * 收款人银行卡号
     */
	@TableField("to_bank_card")
	private String toBankCard;
    /**
     * 收款人银行名字
     */
	@TableField("to_bank_name")
	private String toBankName;
    /**
     * 收款人银行明细
     */
	@TableField("to_bank_detail")
	private String toBankDetail;
	@TableField("create_time")
	private Date createTime;
    /**
     * 确认已经转账。0未确认，1已确认
     */
	@TableField("confirm_status")
	private Integer confirmStatus;
	private String remark;


	public Long getRemitId() {
		return remitId;
	}

	public void setRemitId(Long remitId) {
		this.remitId = remitId;
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

	public String getRemitRealName() {
		return remitRealName;
	}

	public void setRemitRealName(String remitRealName) {
		this.remitRealName = remitRealName;
	}

	public String getRemitBankCard() {
		return remitBankCard;
	}

	public void setRemitBankCard(String remitBankCard) {
		this.remitBankCard = remitBankCard;
	}

	public String getRemitBankName() {
		return remitBankName;
	}

	public void setRemitBankName(String remitBankName) {
		this.remitBankName = remitBankName;
	}

	public Integer getRemitMoney() {
		return remitMoney;
	}

	public void setRemitMoney(Integer remitMoney) {
		this.remitMoney = remitMoney;
	}

	public Integer getRemitDollar() {
		return remitDollar;
	}

	public void setRemitDoller(Integer remitDoller) {
		this.remitDollar = remitDoller;
	}

	public Float getExchange() {
		return exchange;
	}

	public void setExchange(Float exchange) {
		this.exchange = exchange;
	}

	public String getToRealName() {
		return toRealName;
	}

	public void setToRealName(String toRealName) {
		this.toRealName = toRealName;
	}

	public String getToBankCard() {
		return toBankCard;
	}

	public void setToBankCard(String toBankCard) {
		this.toBankCard = toBankCard;
	}

	public String getToBankName() {
		return toBankName;
	}

	public void setToBankName(String toBankName) {
		this.toBankName = toBankName;
	}

	public String getToBankDetail() {
		return toBankDetail;
	}

	public void setToBankDetail(String toBankDetail) {
		this.toBankDetail = toBankDetail;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "BusRemit{" +
			"remitId=" + remitId +
			", userName=" + userName +
			", aliasUserName=" + aliasUserName +
			", remitRealName=" + remitRealName +
			", remitBankCard=" + remitBankCard +
			", remitBankName=" + remitBankName +
			", remitMoney=" + remitMoney +
			", remitDollar=" + remitDollar +
			", exchange=" + exchange +
			", toRealName=" + toRealName +
			", toBankCard=" + toBankCard +
			", toBankName=" + toBankName +
			", toBankDetail=" + toBankDetail +
			", createTime=" + createTime +
			", confirmStatus=" + confirmStatus +
			", remark=" + remark +
			"}";
	}
}
