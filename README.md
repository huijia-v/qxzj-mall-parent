# 工程项目

父项目

---------子项目

----------子项目

pom.xml



huija-zj-mall-parent.      		   父项目

huijia-zj-amll-application  	   入口(controller)

huijia-zj-mall-api          			 接口(controller调用api)

​		--hiujia-zj-cart-api.            购物车api

​		--huijia-zj-pms-api。       商品中心api代码

huijia-zj-mall-service				接口的实现层(api的实现层)

huijia-zj-amll-common			通用工具类

pom.xml

```
msb-dongbao-mall-parent        	父项目
--msb-dongbao-mall-application 	入口（controller）
--msb-dongbao-mall-api			接口（controller调用api）
	--msb-dongbao-cms-api
	--msb-dongbao-cart-api		购物车api代码
    --msb-dongbao-pms-api		商品中心api代码
	--msb-dongbao-dictionary-api
	--msb-dongbao-oms-api
	--msb-dongbao-pay-api
	--msb-dongbao-sms-api
	--msb-dongbao-ums-api

--msb-dongbao-mall-service		接口的实现层(api的实现层)
	--xxxx项目
--msb-dongbao-mall-common		通用工具类
pom.xml
```



```
代码模块介绍
msb-dongbao-mall-parent        	父项目
	msb-dongbao-common 公共包
		msb-dongbao-common-base 公共基础类
		msb-dongbao-common-util 工具类
	msb-dongbao-api 业务模块接口层
		msb-dongbao-oms-api 订单中心接口
		msb-dongbao-pms-api 商品中心接口
		msb-dongbao-ums-api 用户中心接口
		msb-dongbao-pay-api 支付中心接口
		msb-dongbao-cart-api 购物车接口
		msb-dongbao-dictionary-api 基础字典接口
		msb-dongbao-sms-api 优惠中心接口
		msb-dongbao-cms-api 内容中心接口
	msb-dongbao-service 业务模块实现层
		msb-dongbao-oms 订单中心模块实现
		msb-dongbao-pms 商品中心模块实现
		msb-dongbao-ums 用户中心模块实现
		msb-dongbao-pay 支付中心模块实现
		msb-dongbao-cart 购物车模块实现
		msb-dongbao-dictionary 基础字典模块实现
		msb-dongbao-sms 优惠中心模块实现
		msb-dongbao-cms 内容中心模块实现
	msb-dongbao-application web应用模块

	    msb-dongbao-manager-web 后台管理应用
		msb-dongbao-portal-web 商城门户网站
	msb-dongbao-job 定时任务模块

	msb-dongbao-generator 代码生成器
```

# 接口

## 注册接口

通过mybatis-plus自动生成controller、service、dao

```java
public class MallGenerator {
    public static void main(String[] args) {

        //构建一个代码生成对象
        AutoGenerator mpg = new AutoGenerator();

        //配置全局
        GlobalConfig gc = new GlobalConfig();

        String separator = File.separator;
        gc.setOutputDir("/Users/huijia/MyResources/workspace/qxzj-mall-parent/qxzj-service/qxzj-ums/src/main/java");
        gc.setAuthor("huijia");
        gc.setOpen(false); //打开目录

        gc.setFileOverride(true);//是否覆盖

        gc.setServiceName("%sService");//取Service的前缀

        gc.setIdType(IdType.ID_WORKER);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(false);
        mpg.setGlobalConfig(gc);

        DataSourceConfig dsc = new DataSourceConfig();

        dsc.setUrl("jdbc:mysql://localhost:3306/huijia_zj_mall?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai");

        dsc.setDriverName("com.mysql.cj.jdbc.Driver");

        dsc.setUsername("root");

        dsc.setPassword("yl20000304@");

        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);
        //包设置

        // 包设置
        PackageConfig pc = new PackageConfig();

        pc.setParent("com.huijia.qxzjums");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setController("controller");

        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("ums_member");//表名
        strategy.setNaming(NamingStrategy.underline_to_camel);// 下划线转他驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);// 列 下划线转脱发
        strategy.setEntityLombokModel(true);//lombok 开启
        strategy.setLogicDeleteFieldName("deleted");

        // 自动填充
        TableFill gmtCreate = new TableFill("create_time", FieldFill.INSERT);
        TableFill gmtModify = new TableFill("update_time",FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<TableFill>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModify);

        strategy.setTableFillList(tableFills);

        //乐观锁
        strategy.setVersionFieldName("version");

        // restcontroller
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);// localhost:xxx/hello_2

        mpg.setStrategy(strategy);

        mpg.execute();
    }
}

```



数据库标准

- 表必备字段：id、gmt_create、gmt_modified
- 更新时间的默认设置：不要让数据库来控制



创建时间和更新时间，用mybatis-plus的handler进行统一管理

```java
package com.huijia.qxzjums.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component //一定要机上注解，这样spring容器才能管理这个Bean
public class MyHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("添加插入时间");
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("添加更新时间");
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}

```

在实体类上加上注解` @TableField`

```java
package com.huijia.qxzjums.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 后台用户表
 * </p>
 *
 * @author huijia
 * @since 2022-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class UmsMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    /**
     * 头像
     */
    private String icon;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 备注信息
     */
    private String note;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 最后登录时间
     */
    private Date loginTime;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    /**
     * 帐号启用状态：0->禁用；1->启用
     */
    private Integer status;

}

```



定义DO/DTO/VO等POJO类时，不要设定任何属性默认值。

DO：data object 和数据库表对应，可以1个do对应多个表

DTO：trans转换dao输出的对象，do和dto不绝对一样，dto可以对数据做额外的处理，do整理后需要向上传递时用

VO：渲染视图。VO和DO有时也相同

当do用于页面渲染用分页时，需要vo、do做一些特殊处理时，用dto

POJO：属性，set，get，object方法，没有业务含义

POJO是纯净的

设置默认值，会让·错误不好排查





# 用户名不能重复

字段，唯一索引

加一次校验，如果有这个名字，不能注册



# 密码不能明文存储

防止被拖库

脱敏（密码(非对称加密）

- hash
- 对称加密（手机号、身份证）

## 加密常用的方式

Md5:

![image-20221121222547047](https://huijiav.oss-cn-hangzhou.aliyuncs.com/img/202211212225302.png)





![image-20221121221431869](https://huijiav.oss-cn-hangzhou.aliyuncs.com/img/202211212215620.png)

![image-20221121221519988](https://huijiav.oss-cn-hangzhou.aliyuncs.com/img/202211212215707.png)

![image-20221121221924035](https://huijiav.oss-cn-hangzhou.aliyuncs.com/img/202211212219035.png)

![image-20221121222132153](https://huijiav.oss-cn-hangzhou.aliyuncs.com/img/202211212221063.png)

# 统一返回值

## 前后端分离，后端接口

```json
{
  "code": 状态码,
  "msg": "信息体式",
  "data": 
}
  
 //data:object 	或者 list
  
  
```

使˙状态码统一管理，便于前端处理





# 参数校验



引入maven

```xml
  <!--spring validation 校验-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
```



在接受参数的地方添加校验注解`@Valid`

```java
/**
     * 注册
     * @param umsMemberRegisterParamDTO 注册实体类
     * @return
     */
    @GetMapping("/register")
    public ResultWrapper register(@RequestBody @Valid UmsMemberRegisterParamDTO umsMemberRegisterParamDTO) {

        umsMemberService.register(umsMemberRegisterParamDTO );
        return ResultWrapper.getSuccessBuilder().data(null).build();
    }
```

在实体类添加约束

```java
 @NotEmpty(message = "用户名不为空")
    private String username;
```

在portal-web模块编写ValidateHandler类

```java
package com.huijia.qxzj.portal.web.advice;

import com.huijia.qxzj.common.base.result.ResultWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author huijia
 * @Email: 2921523918@qq.com
 * @date 2022/11/22 3:18 下午
 */
@ControllerAdvice  //该注解是通过AOP实现的
public class ValidateHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {


        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String defaultMessage = fieldError.getDefaultMessage();
            sb.append(" " + defaultMessage);
            break;

        }
        return new ResponseEntity(ResultWrapper.builder().code(102).msg(sb.toString()).build(), HttpStatus.OK);
    }

}

```





ps：当有拷贝欲望的时候，就得考虑是不是想错了，是不是有更好的方法

代码复用

# 统一异常处理

```java
package com.huijia.qxzj.portal.web.advice;

import com.huijia.qxzj.common.base.result.ResultWrapper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

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
    @ExceptionHandler(Exception.class)
    public ResultWrapper customException() {
        return ResultWrapper.builder().code(301).msg("统一异常").build();
    }
}

```

# JWT

```
eyJhbGciOiJIUzI1NiJ9.(base.encode(header))
eyJzdWIiOiLmlZnogrIifQ.(base64(playload)) 载荷，信息
HGHY3FZU8OHOR_e79-iHafDfDKoI6T4LwlST5Oj_kDs（散列加密（playload，盐））
```



![image-20221123152928054](/Users/huijia/Library/Application Support/typora-user-images/image-20221123152928054.png)



登录后不需要再输入密码和用户名

md5、sha不可逆·，属于散列算法

Base64是编码器，可以编码，可以解码

散列算法：md5，sha

对称加密：DES，3DES，AES

非对称加密：公加私解、私加公解



# 设计拦截器

<img src="https://huijiav.oss-cn-hangzhou.aliyuncs.com/img/202211222143586.png" alt="image-20221122214236518" style="zoom: 67%;" />

使用注解的方式

在base模块编写注解

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenCheck {
    //是否校验token,默认token
    boolean required() default true;
}
```

在controller层需要用到的地方添加编辑好的注解

```java
   @PostMapping("/edit")
    @TokenCheck
    public ResultWrapper edit(@RequestBody UmsMember umsMember) {

        System.out.println("edit");

        return umsMemberService.edit(umsMember);
    }
```



在拦截器中编写注解解析器

```java
package com.huijia.qxzj.portal.web.interceptor;

import com.huijia.qxzj.common.base.annotations.TokenCheck;
import com.huijia.qxzj.common.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author huijia
 * @Email: 2921523918@qq.com
 * @date 2022/11/22 9:51 下午
 */

public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("拦截器进入");

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            throw new LoginException("token 为空");
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(TokenCheck.class)){
            TokenCheck annotation = method.getAnnotation(TokenCheck.class);
            if (annotation.required()){
                // 校验token
                try {
                    String s = JwtUtil.parseToken(token);
                    System.out.println(s);
                    System.out.println("没有异常");
                    return true;
                }catch (Exception e){
                    throw new LoginException("token 异常");
                }

            }
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

```



编写拦截器的配置类

```java
package com.huijia.qxzj.portal.web.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author huijia
 * @Email: 2921523918@qq.com
 * @date 2022/11/22 10:27 下午
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        		registry.addInterceptor(authInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/user-member/register")
				.excludePathPatterns("/user-member/login")
				.excludePathPatterns("/code/generator");
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }
}

```



# 验证码

## jcaptcha



导入maven

```xml
    <!-- https://mvnrepository.com/artifact/com.octo.captcha/jcaptcha -->
        <dependency>
            <groupId>com.octo.captcha</groupId>
            <artifactId>jcaptcha</artifactId>
            <version>1.0</version>
        </dependency>
```

需要手动画

```java
package com.huijia.qxzj.portal.web.util;

import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;


/**
 * @author huijia
 * @Email: 2921523918@qq.com
 * @date 2022/11/23 10:43 下午
 */
public class JcaptchaUtil {



    //做成单例
    private static final ImageCaptchaService service = imageCaptchaService();

    public static ImageCaptchaService getService() {
        return service;
    }

    private static ImageCaptchaService imageCaptchaService() {

        //背景
        UniColorBackgroundGenerator backgroundGenerator = new UniColorBackgroundGenerator(100, 50);
        RandomRangeColorGenerator textColor = new RandomRangeColorGenerator(new int[]{0, 100}, new int[]{0, 200}, new int[]{50, 250});

        RandomTextPaster randomTextPaster = new RandomTextPaster(4, 5, textColor);


        RandomFontGenerator randomFontGenerator = new RandomFontGenerator(20, 30);

        //组装图像
        ComposedWordToImage composedWordToImage = new ComposedWordToImage(randomFontGenerator, backgroundGenerator, randomTextPaster);

        //
        ComposeDictionaryWordGenerator cdwg = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));

        GimpyFactory gf = new GimpyFactory(cdwg, composedWordToImage);

        GenericCaptchaEngine gce = new GenericCaptchaEngine(new CaptchaFactory[]{gf});

        return new GenericManageableCaptchaService(gce, 20, 2000, 2000);
    }
}

```



生成图像和测试

```java
package com.huijia.qxzj.portal.web.controller;

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
 * @Email: 2921523918@qq.com
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

```



## happy-captcha

导入maven

```xml
 <dependency>
            <groupId>com.ramostear</groupId>
            <artifactId>Happy-Captcha</artifactId>
            <version>1.0.1</version>
        </dependency>
```



这个极为简单，可以直接编写controller

```java
package com.msb.dongbao.portal.web.controller.studyCaptcha;

import com.msb.dongbao.common.base.annotations.TokenCheck;
import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaStyle;
import com.ramostear.captcha.support.CaptchaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 马士兵教育:chaopengfei
 * @date 2021/4/9
 */
@RestController
@RequestMapping("/happy-captcha")
public class HappyCaptchaController {

	@GetMapping("/generator")
	@TokenCheck(required = false)
	public void generatorCode(HttpServletRequest request, HttpServletResponse response) {

		HappyCaptcha.require(request,response)
				.style(CaptchaStyle.ANIM)
				.type(CaptchaType.ARITHMETIC_ZH)
				.build().finish();
	}


	@GetMapping("/verify")
	@TokenCheck(required = false)
	public String verify(String verifyCode, HttpServletRequest request) {

		Boolean aBoolean = HappyCaptcha.verification(request,verifyCode,true);
		if (aBoolean){
			HappyCaptcha.remove(request);
			return "通过";
		}

		return "不通过";
	}
}

```

## Easy-captcha

导入maven

```xml
   <dependency>
            <groupId>com.github.whvcse</groupId>
            <artifactId>easy-captcha</artifactId>
            <version>1.6.2</version>

        </dependency>
```

```java
package com.huijia.qxzj.portal.web.controller;

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
 * @Email: 2921523918@qq.com
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


```

## kcaptcha



导入maven

```xml
 <!--kcaptcha-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>kaptcha-spring-boot-starter</artifactId>
            <version>1.1.0</version>
        </dependency>
```



中间件思维：将业务模版交给客户端去做





