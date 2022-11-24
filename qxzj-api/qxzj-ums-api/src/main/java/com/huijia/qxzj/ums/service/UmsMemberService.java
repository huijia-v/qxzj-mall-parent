package com.huijia.qxzj.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huijia.qxzj.common.base.result.ResultWrapper;
import com.huijia.qxzj.ums.entity.UmsMember;
import com.huijia.qxzj.ums.entity.dto.UmsMemberLoginParamDTO;
import com.huijia.qxzj.ums.entity.dto.UmsMemberRegisterParamDTO;


/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author huijia
 * @since 2022-11-19
 */

public interface UmsMemberService extends IService<UmsMember> {
    public ResultWrapper register(UmsMemberRegisterParamDTO umsMemberRegisterParamDTO);

    public ResultWrapper login(UmsMemberLoginParamDTO umsMemberLoginParamDTO);

    ResultWrapper edit(UmsMember umsMember);
}
