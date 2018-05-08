package com.shunyin.pojo;

import java.util.Date;

/**
 * Title: todoedit
 * Description: todoedit
 * author: wenjun
 * date: 2018/5/2 11:15
 */
public class BookUserQuery {
    private String type;
    private String userName;
    private String aliasUserName;
    private String serialNo;
    private Date beginTime;
    private Date endTime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "BookUserQuery{" +
                "type='" + type + '\'' +
                ", userName='" + userName + '\'' +
                ", aliasUserName='" + aliasUserName + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                '}';
    }
}
