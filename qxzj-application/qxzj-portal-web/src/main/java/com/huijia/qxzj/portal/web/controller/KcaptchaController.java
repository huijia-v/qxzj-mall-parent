package com.huijia.qxzj.portal.web.controller;

import com.baomidou.kaptcha.Kaptcha;
import com.baomidou.kaptcha.exception.KaptchaException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.huijia.qxzj.common.base.annotations.TokenCheck;
import com.huijia.qxzj.portal.web.custom.MyGoogleKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huijia
 * @date 2022/11/24 5:48 下午
 */
@RestController
@RequestMapping("/kcaptcha")
public class KcaptchaController {

    @Autowired
    private Kaptcha kaptcha;


    @GetMapping("/generator")
    @TokenCheck(required = false)
    public void generatorCode(HttpServletRequest request, HttpServletResponse response) {
        String s = kaptcha.toString();
        System.out.println(s);
        String s1 = kaptcha.render().toString();
        System.out.println(s1);
    }

    @GetMapping("/verify")
    public String verify(String code, HttpServletRequest request) {

        try {
            System.out.println(code);

            Boolean aBoolean = kaptcha.validate(code);

            if (aBoolean) {
                return "通过";
            }
        } catch (Exception e) {

            System.out.println(e.toString());
        }
        return "不通过";
    }


    @GetMapping("/verify2")
    public String verify2(String code, HttpServletRequest request) {


        System.out.println(code);

        Boolean aBoolean = kaptcha.validate(code);

        if (aBoolean) {
            return "通过";
        }

        return "不通过";
    }

    @Autowired
    MyGoogleKaptcha myGoogleKaptcha;

    @GetMapping("/generator-my")
    public void generatorCodeMy(HttpServletRequest request, HttpServletResponse response) {
        myGoogleKaptcha.render();
    }

    @GetMapping("/verify-my")
    public String verifyMy(String verifyCode, HttpServletRequest request) {

        Boolean aBoolean = myGoogleKaptcha.validate(verifyCode, 10);
        if (aBoolean) {
            return "通过";
        }
        return "不通过";
    }
}

