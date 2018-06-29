package com.minxin.base.web.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by todd on 2016/11/10.
 *
 * @author todd
 */
public class Model implements Serializable {

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private String createUser;

    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private String updateUser;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
