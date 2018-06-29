package com.minxin.base.web.security;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Created by todd on 2017/1/9.
 *
 * @author todd
 */
public class SecurityUser implements Serializable {
    /**
     * 用户id
     */
    private int id;

    /**
     * 用户账号
     */
    private String name;

    /**
     * 用户中文名称
     */
    private String nameZh;

    /**
     * 性别
     */
    private int gender;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户状态
     */
    private int status;

    /**
     * 图片地址
     */
    private String pictureUrl;

    /**
     * 当前所在公司id
     */
    private int companyId;

    /**
     * 用户分配的系统
     */
    private Set<Map> systems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Set<Map> getSystems() {
        return systems;
    }

    public void setSystems(Set<Map> systems) {
        this.systems = systems;
    }
}
