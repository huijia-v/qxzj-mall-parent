package com.huijia.qxzjgenerator;

//import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.ArrayList;

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
