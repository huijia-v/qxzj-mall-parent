package com.huijia.qxzj.common.base.result;

import com.huijia.qxzj.common.base.enums.StateCodeEnum;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author huijia
 * @Email: 2921523918@qq.com
 * @date 2022/11/22 2:28 下午
 */
@Data
@Builder
public class ResultWrapper<T> implements Serializable {



    // 状态码
    private int code;

    // 提示信息
    private String msg;

    //数据
    private T data;


    /**
     *  返回成功的包装
     * @return
     */
    public  static ResultWrapper.ResultWrapperBuilder getSuccessBuilder(){
        return ResultWrapper.builder().code(StateCodeEnum.SUCCESS.getCode()).msg(StateCodeEnum.SUCCESS.getMsg());
    }

    public static ResultWrapper.ResultWrapperBuilder getFailBuilder(){
        return ResultWrapper.builder().code(StateCodeEnum.FAIL.getCode()).msg(StateCodeEnum.FAIL.getMsg());
    }
}
