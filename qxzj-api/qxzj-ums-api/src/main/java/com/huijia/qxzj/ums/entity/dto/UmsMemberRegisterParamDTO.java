package com.huijia.qxzj.ums.entity.dto;


import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@ToString
public class UmsMemberRegisterParamDTO {

    @Size(min = 1,max = 8,message = "用户名长度在1-8之间")
    private String username;

    private String password;

    private String icon;

    @Email
    private String email;

    private String nickName;



}
