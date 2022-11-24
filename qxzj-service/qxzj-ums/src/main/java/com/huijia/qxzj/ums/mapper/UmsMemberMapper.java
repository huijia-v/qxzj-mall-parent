package com.huijia.qxzj.ums.mapper;

import com.huijia.qxzj.ums.entity.UmsMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author huijia
 * @since 2022-11-19
 */

@Repository
public interface UmsMemberMapper extends BaseMapper<UmsMember> {


    UmsMember selectByName(String username);


}
