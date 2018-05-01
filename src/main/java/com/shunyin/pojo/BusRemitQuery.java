package com.shunyin.pojo;

/**
 * Title: todoedit
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/28 15:35
 */
public class BusRemitQuery {
    private Long userName;
    private String aliasUserName;
    private String realName;
    private Integer confirmStatus;
    // 时间段暂略

    public Long getUserName() {
        return userName;
    }

    public void setUserName(Long userName) {
        this.userName = userName;
    }

    public String getAliasUserName() {
        return aliasUserName;
    }

    public void setAliasUserName(String aliasUserName) {
        this.aliasUserName = aliasUserName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }
}
