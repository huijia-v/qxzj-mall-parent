package com.huijia.qxzj.ums.service.impl;

import com.huijia.qxzj.common.base.enums.StateCodeEnum;
import com.huijia.qxzj.common.base.result.ResultWrapper;
import com.huijia.qxzj.common.util.JwtUtil;
import com.huijia.qxzj.ums.entity.UmsMember;
import com.huijia.qxzj.ums.entity.dto.UmsMemberLoginParamDTO;
import com.huijia.qxzj.ums.entity.dto.UmsMemberRegisterParamDTO;
import com.huijia.qxzj.ums.entity.response.UserMemberLoginResponse;
import com.huijia.qxzj.ums.mapper.UmsMemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huijia.qxzj.ums.service.UmsMemberService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author huijia
 * @since 2022-11-19
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    UmsMemberMapper umsMemberMapper;

    @Autowired
    UmsMemberService umsMemberService;




    @Override
    public ResultWrapper register(UmsMemberRegisterParamDTO umsMemberRegisterParamDTO) {


        UmsMember umsMember = new UmsMember();

        BeanUtils.copyProperties(umsMemberRegisterParamDTO, umsMember);

        //加密🔐
        String encode = passwordEncoder.encode(umsMemberRegisterParamDTO.getPassword());
        umsMember.setPassword(encode);

        umsMemberMapper.insert(umsMember);
        return ResultWrapper.getSuccessBuilder().build();
    }

    @Override
    public ResultWrapper login(UmsMemberLoginParamDTO umsMemberLoginParamDTO) {

        UmsMember umsMember = umsMemberMapper.selectByName(umsMemberLoginParamDTO.getUsername());
        if(null !=  umsMember){
            String passwordDb = umsMember.getPassword();

            if(!passwordEncoder.matches(umsMemberLoginParamDTO.getPassword(),passwordDb)){
                return ResultWrapper.getFailBuilder().code(StateCodeEnum.PASSWORD_ERROR.getCode()).msg(StateCodeEnum.PASSWORD_ERROR.getMsg()).build();
            }

        }else{
            return ResultWrapper.getFailBuilder().code(StateCodeEnum.USER_EMPTY.getCode()).msg(StateCodeEnum.USER_EMPTY.getMsg()).build();
        }

        String token = JwtUtil.createToken(umsMember.getId() + "");

        UserMemberLoginResponse userMemberLoginResponse = new UserMemberLoginResponse();
        userMemberLoginResponse.setToken(token);


        //将密码隐去
        umsMember.setPassword("不好意思，密码是要给用户保密的");

        userMemberLoginResponse.setUmsMember(umsMember);
        return ResultWrapper.getSuccessBuilder().data(userMemberLoginResponse).build();
    }


    @Override

    public ResultWrapper edit(UmsMember umsMember) {
        umsMemberMapper.updateById(umsMember);

        return ResultWrapper.getSuccessBuilder().data(umsMember).build();
    }
}
