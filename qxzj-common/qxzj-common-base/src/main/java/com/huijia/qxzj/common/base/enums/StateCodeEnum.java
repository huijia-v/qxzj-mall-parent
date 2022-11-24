package com.huijia.qxzj.common.base.enums;

import lombok.Data;

/**
 * @author huijia
 * @Email: 2921523918@qq.com
 * @date 2022/11/22 2:23 下午
 */


public enum StateCodeEnum {


    /**
     * 用户名密码不正确
     */
    PASSWORD_ERROR(1001,"用户名密码不正确"),

    /**
     * 用户不存在
     */
    USER_EMPTY(1002,"用户不存在"),

    /**
     * 请求失败
     */
    FAIL(500,"请求失败"),
    /**
     * 请求成功
     */
    SUCCESS(200,"请求成功");


    private  int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    StateCodeEnum(int code , String msg){
        this.code = code;
        this.msg = msg;
    }
}
