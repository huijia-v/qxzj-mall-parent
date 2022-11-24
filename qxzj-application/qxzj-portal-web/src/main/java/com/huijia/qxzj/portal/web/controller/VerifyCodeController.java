package com.huijia.qxzj.portal.web.controller;

import com.huijia.qxzj.common.base.annotations.TokenCheck;
import com.huijia.qxzj.portal.web.code.ImageCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * @author huijia
 * @Email: 2921523918@qq.com
 * @date 2022/11/23 5:12 下午
 */

@RestController
@RequestMapping("/code")
public class VerifyCodeController {

    String attrName = "verifyCode";

    @GetMapping("/generator")
//    @TokenCheck(required = false)
    public void generatorCode(HttpServletRequest request, HttpServletResponse response) {

        try {
            ImageCode imageCode = ImageCode.getInstance();

//            codeValue
            String code = imageCode.getCode();

//            request.set().setAttribute(attrName, code);
            request.getSession().setAttribute(attrName, code);

            //图片
            ByteArrayInputStream image = imageCode.getImage();

            response.setContentType("image/jpeg");

            byte[] bytes = new byte[1025];

            try (ServletOutputStream out = response.getOutputStream()) {
                while (image.read(bytes) != -1) {
                    out.write(bytes);

                }
            }
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    @GetMapping("/verifyCode")
//    @TokenCheck(required = false)
    public String verify( String code, HttpServletRequest request) {

        System.out.println(code);


        String realCode = request.getSession().getAttribute(attrName).toString();
        System.out.println(realCode);
        if (code.equals(realCode)) {
            return "验证码校验通过";
        }
        return "code不通过";
    }



    @GetMapping("/generator-base64")
//    @TokenCheck(required = false)
    public String generatorCodeBase64(HttpServletRequest request, HttpServletResponse response) {

        try {
            ImageCode imageCode = ImageCode.getInstance();

//            codeValue
            String code = imageCode.getCode();

//            request.set().setAttribute(attrName, code);
            request.getSession().setAttribute(attrName, code);

            //图片
            ByteArrayInputStream image = imageCode.getImage();


            request.getSession().setAttribute(attrName, code);

            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int r = 0;
            while ((r = image.read(buff, 0, 1024)) > 0 ) {
                swapStream.write(buff, 0, r);

            }

            byte[] data = swapStream.toByteArray();

            return Base64.getEncoder().encodeToString(data);

        } catch (Exception e) {
            System.out.println("error");
            return "";
        }

    }

}
