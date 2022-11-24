package com.huijia.qxzj.portal.web.advice;

import com.baomidou.kaptcha.exception.KaptchaException;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.huijia.qxzj.common.base.TokenException;
import com.huijia.qxzj.common.base.result.ResultWrapper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.LoginException;

/**
 * @author huijia
 * @Email: 2921523918@qq.com
 * @date 2022/11/22 4:17 下午
 */

//@ControllerAdvice
//@RestController  //把返回值设置成json  RestBody也可以，如果不设置就会以返回值的结果作为某个页面，但是此时是不可能有页面的，所以会报404
////@RestControllerAdvice 相等于 ControllerAdvice 和 @RestController
@RestControllerAdvice
public class GlobalExceptionHandle {


    //所有的异常都走这里
    @ExceptionHandler(ArithmeticException.class)
    public ResultWrapper customException() {

        return ResultWrapper.builder().code(301).msg("统一异常").build();
    }


    /**
     * 自定义 token异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(TokenException.class)
    public ResultWrapper loginException(Exception e){
        return ResultWrapper.getFailBuilder().code(501).msg(e.getMessage()).build();
    }

    @ExceptionHandler(KaptchaException.class)
    public String kcaptchaException(KaptchaException e){
        if (e instanceof KaptchaTimeoutException){
            return "超时";
        }else if (e instanceof KaptchaIncorrectException){
            return "不正确";
        }else if (e instanceof KaptchaNotFoundException){
            return "没找到";
        }else {
            return "反正错了";
        }
    }

}
