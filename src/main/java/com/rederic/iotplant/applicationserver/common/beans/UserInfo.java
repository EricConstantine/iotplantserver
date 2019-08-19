package com.rederic.iotplant.applicationserver.common.beans;

public class UserInfo {

    public String avator;
    public String name;
    public String user_id;
    public String access;

    public UserInfo(String avator, String name, String user_id, String access) {
        this.avator = avator;
        this.name = name;
        this.user_id = user_id;
        this.access = access;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

}
