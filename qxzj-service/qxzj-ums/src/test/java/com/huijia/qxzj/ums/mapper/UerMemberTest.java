package com.huijia.qxzj.ums.mapper;

import com.huijia.qxzj.ums.QxzjUmsApplication;
import com.huijia.qxzj.ums.entity.UmsMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest(classes = QxzjUmsApplication.class)
public class UerMemberTest {

    @Autowired
    UmsMemberMapper umsMemberMapper;

    @Test
    void testInsert(){

        UmsMember t = new UmsMember();
        t.setUsername("cpf655535");
        t.setStatus(0);
        t.setPassword("1");
        t.setNote("note");
        t.setNickName("nick");
        t.setEmail("email");


        umsMemberMapper.insert(t);

//		UmsMember cpf1 = umsMemberMapper.selectByName("nick");
//		Long id = cpf1.getId();
//		System.out.println("id:"+id);

    }

    @Test
    void testUpdate(){
        UmsMember t = new UmsMember();
        t.setNickName("尴尬65535");
        t.setEmail("@demo3.com");
        t.setId(66L);

        umsMemberMapper.updateById(t);
    }

    @Test
    public void t2() {
        System.out.println(new Date());

    }
}
