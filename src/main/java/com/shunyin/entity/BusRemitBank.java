package com.shunyin.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wenjun
 * @since 2018-05-10
 */
@TableName("bus_remit_bank")
public class BusRemitBank implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableField("card_id")
	private Long cardId;
    /**
     * 银行卡号
     */
	@TableField("card_no")
	private String cardNo;
    /**
     * 真实姓名
     */
	@TableField("real_name")
	private String realName;
    /**
     * 银行名称
     */
	@TableField("bank_name")
	private String bankName;
    /**
     * 简称
     */
	@TableField("bank_short_name")
	private String bankShortName;
    /**
     * 开户明细
     */
	@TableField("bank_detail")
	private String bankDetail;
	@TableField("img_url")
	private String imgUrl;
	@TableField("sort_id")
	private Integer sortId;
	private Integer status;
	@TableField("created_time")
	private Date createdTime;


	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankShortName() {
		return bankShortName;
	}

	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}

	public String getBankDetail() {
		return bankDetail;
	}

	public void setBankDetail(String bankDetail) {
		this.bankDetail = bankDetail;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public String toString() {
		return "BusRemitBank{" +
			"cardId=" + cardId +
			", cardNo=" + cardNo +
			", realName=" + realName +
			", bankName=" + bankName +
			", bankShortName=" + bankShortName +
			", bankDetail=" + bankDetail +
			", imgUrl=" + imgUrl +
			", sortId=" + sortId +
			", status=" + status +
			", createdTime=" + createdTime +
			"}";
	}
}
