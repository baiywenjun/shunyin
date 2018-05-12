package com.shunyin.entity;

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
 * @since 2018-05-12
 */
@TableName("sys_certification")
public class SysCertification implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("cert_id")
	private Long certId;
	@TableField("id_front_file_path")
	private String idFrontFilePath;
	@TableField("id_back_file_path")
	private String idBackFilePath;
	@TableField("card_front_file_path")
	private String cardFrontFilePath;
	@TableField("id_front_url")
	private String idFrontUrl;
	@TableField("id_back_url")
	private String idBackUrl;
	@TableField("card_front_url")
	private String cardFrontUrl;
	@TableField("create_time")
	private Date createTime;
	@TableField("is_user_id")
	private Long isUserId;


	public Long getCertId() {
		return certId;
	}

	public void setCertId(Long certId) {
		this.certId = certId;
	}

	public String getIdFrontFilePath() {
		return idFrontFilePath;
	}

	public void setIdFrontFilePath(String idFrontFilePath) {
		this.idFrontFilePath = idFrontFilePath;
	}

	public String getIdBackFilePath() {
		return idBackFilePath;
	}

	public void setIdBackFilePath(String idBackFilePath) {
		this.idBackFilePath = idBackFilePath;
	}

	public String getCardFrontFilePath() {
		return cardFrontFilePath;
	}

	public void setCardFrontFilePath(String cardFrontFilePath) {
		this.cardFrontFilePath = cardFrontFilePath;
	}

	public String getIdFrontUrl() {
		return idFrontUrl;
	}

	public void setIdFrontUrl(String idFrontUrl) {
		this.idFrontUrl = idFrontUrl;
	}

	public String getIdBackUrl() {
		return idBackUrl;
	}

	public void setIdBackUrl(String idBackUrl) {
		this.idBackUrl = idBackUrl;
	}

	public String getCardFrontUrl() {
		return cardFrontUrl;
	}

	public void setCardFrontUrl(String cardFrontUrl) {
		this.cardFrontUrl = cardFrontUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getIsUserId() {
		return isUserId;
	}

	public void setIsUserId(Long isUserId) {
		this.isUserId = isUserId;
	}

	@Override
	public String toString() {
		return "SysCertification{" +
			"certId=" + certId +
			", idFrontFilePath=" + idFrontFilePath +
			", idBackFilePath=" + idBackFilePath +
			", cardFrontFilePath=" + cardFrontFilePath +
			", idFrontUrl=" + idFrontUrl +
			", idBackUrl=" + idBackUrl +
			", cardFrontUrl=" + cardFrontUrl +
			", createTime=" + createTime +
			", isUserId=" + isUserId +
			"}";
	}
}
