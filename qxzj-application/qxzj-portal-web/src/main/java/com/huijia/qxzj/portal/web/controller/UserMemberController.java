package com.huijia.qxzj.portal.web.controller;


import com.huijia.qxzj.common.base.annotations.TokenCheck;
import com.huijia.qxzj.common.base.result.ResultWrapper;
import com.huijia.qxzj.common.util.JwtUtil;
import com.huijia.qxzj.ums.entity.UmsMember;
import com.huijia.qxzj.ums.entity.dto.UmsMemberLoginParamDTO;
import com.huijia.qxzj.ums.entity.dto.UmsMemberRegisterParamDTO;
import com.huijia.qxzj.ums.service.UmsMemberService;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/user-member")
public class UserMemberController {

    @Autowired
    UmsMemberService umsMemberService;


    @GetMapping("/hello")
    @TokenCheck
    public String hello() {
        return "hello";
    }


    /**
     * 注册
     * @param umsMemberRegisterParamDTO 注册实体类
     * @return
     */
    @PostMapping("/register")
    public ResultWrapper register(@RequestBody @Valid UmsMemberRegisterParamDTO umsMemberRegisterParamDTO) {


        return umsMemberService.register(umsMemberRegisterParamDTO);
    }

    /**
     * 登录
     * @param umsMemberLoginParamDTO 登录dto
     * @return
     */
    @PostMapping("/login")
    public ResultWrapper login(@RequestBody UmsMemberLoginParamDTO umsMemberLoginParamDTO) {

        return umsMemberService.login(umsMemberLoginParamDTO);
    }


    @GetMapping("/tewt-verify")
    public String verify(String token) {

//        String s = JwtUtil.parseToken(token);

//        String s = token;
//        return s;


//        String code = request.getSession().getAttribute("1234").toString();

//        System.out.println(verifycode);
//        System.out.println(code);
//        if (verifycode.equals(code)) {
//            return "验证码校验通过";
//        }
        return "code不通过";
    }


    @PostMapping("/edit")
    @TokenCheck
    public ResultWrapper edit(@RequestBody UmsMember umsMember) {

        System.out.println("edit");

        return umsMemberService.edit(umsMember);
    }

}
