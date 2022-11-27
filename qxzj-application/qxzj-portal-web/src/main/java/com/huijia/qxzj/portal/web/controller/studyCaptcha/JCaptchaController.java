package com.huijia.qxzj.portal.web.controller.studyCaptcha;

import com.huijia.qxzj.portal.web.code.ImageCode;
import com.huijia.qxzj.portal.web.util.JcaptchaUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * @author huijia
 * @date 2022/11/23 5:12 下午
 */

@RestController
@RequestMapping("/jcptcha")
public class JCaptchaController {

    String attrName = "verifyCode";

    @GetMapping("/jcgenerator")
//    @TokenCheck(required = false)
    public void jcgeneratorCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getSession().getId();
            BufferedImage bufferedImage = JcaptchaUtil.getService().getImageChallengeForID(id);


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(byteArrayOutputStream);


            jpegEncoder.encode(bufferedImage);

            response.setHeader("Cache-Control", "no-store");
            response.setContentType("image/jpeg");
            byte[] bytes = byteArrayOutputStream.toByteArray();
            ServletOutputStream servletOutputStream = response.getOutputStream();

            servletOutputStream.write(bytes);
            servletOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/jpcverify")
    public String veriify(HttpServletRequest request, String code) {

        Boolean aBoolean = JcaptchaUtil.getService().validateResponseForID(request.getSession().getId(), code);

        if (aBoolean) {
            return "ok";
        }

        return "error";
    }
}
