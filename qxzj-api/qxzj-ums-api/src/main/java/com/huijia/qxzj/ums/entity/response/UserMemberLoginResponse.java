package com.huijia.qxzj.ums.entity.response;

import com.huijia.qxzj.ums.entity.UmsMember;
import lombok.Data;

/**
 * @author huijia
 * @Email: 2921523918@qq.com
 * @date 2022/11/22 9:13 下午
 */

@Data
public class UserMemberLoginResponse {

    private String token;

    private UmsMember umsMember;

}
