package com.huijia.qxzj.portal.web.controller.studyCaptcha;

import com.huijia.qxzj.common.base.annotations.TokenCheck;
import com.ramostear.captcha.HappyCaptcha;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author huijia
 * @date 2022/11/24 4:31 下午
 */
@RestController
@RequestMapping("/easy-captcha")
public class EasyCaptchaController {

    @GetMapping("/generator")
    @TokenCheck(required = false)
    public void generatorCode(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 基础
//			CaptchaUtil.out(request, response);

            // 算术
			ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(200,50);
			// 3个数的运算
			arithmeticCaptcha.setLen(3);
			arithmeticCaptcha.text();

			CaptchaUtil.out(arithmeticCaptcha,request,response);

            ChineseCaptcha chineseCaptcha = new ChineseCaptcha(150,50);
            CaptchaUtil.out(chineseCaptcha,request,response);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @GetMapping("/verify")
    @TokenCheck(required = false)
    public String verify(String verifyCode, HttpServletRequest request) {

        Boolean aBoolean = CaptchaUtil.ver(verifyCode, request);
        if (aBoolean) {

            CaptchaUtil.clear(request);
            return "通过";
        }

        return "不通过";
    }





    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/generator-redis")
    @TokenCheck(required = false)
    public Map<String, String> generatorCodeRedis(HttpServletRequest request, HttpServletResponse response) {


        SpecCaptcha specCaptcha = new SpecCaptcha(100, 50);

        String text = specCaptcha.text();
        System.out.println("验证码：" + text);

        String uuid = UUID.randomUUID().toString();

        String sessionId = request.getSession().getId();

        stringRedisTemplate.opsForValue().set(uuid,text);

        String s1 = specCaptcha.toBase64();
        System.out.println("base64:"+s1);
        Map<String,String> map = new HashMap<>();
        map.put("uuid",uuid);
        map.put("base64",s1);

        return map;

    }

    @GetMapping("/verify-redis")
    @TokenCheck(required = false)
    public String verifyRedis(String verifyCode, HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        String c = stringRedisTemplate.opsForValue().get(sessionId);

        if (verifyCode.equals(c)) {
            HappyCaptcha.remove(request);
            return "通过";
        }

        return "不通过";
    }



}

