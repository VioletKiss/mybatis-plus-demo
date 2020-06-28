package cn.violetkiss.mybatisplus.generator;

import cn.violetkiss.mybatisplus.config.DataSourceProp;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wrb
 */
public class MybatisPlusGenerate {

    //TODO 指定生成的bean的数据库表名
    private static String[] table_lists = new String[]{
            "user", "time"
    };
    //TODO 生成的作者名
    private static String author = "wrb";
    //TODO 父目录路径
    private static String parent = "cn.violetkiss.mybatisplus";
    //TODO 可选 项目名
    private static String moduleName = "";
    //TODO 文件输出目录
    private static String outputDir = System.getProperty("user.dir") + "/src/main/java";

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 数据源配置
        mpg.setDataSource(getDataSourceConfig());
        // 数据库表配置
        mpg.setStrategy(getStrategyConfig());
        // 包配置
        mpg.setPackageInfo(getPackageConfig());
        // 模板配置
        mpg.setTemplate(getTemplateConfig());
        // 全局配置
        mpg.setGlobalConfig(getGlobalConfig());
        // 自定义配置
        mpg.setCfg(getInjectionConfig());
        mpg.execute();
    }

    private static DataSourceConfig getDataSourceConfig() {
        // 数据源配置
        DataSourceProp dbp = new DataSourceProp();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(dbp.getDbType());
        dataSourceConfig.setUrl(dbp.getUrl());
        dataSourceConfig.setDriverName(dbp.getDriverName());
        dataSourceConfig.setUsername(dbp.getUsername());
        dataSourceConfig.setPassword(dbp.getPassword());
        return dataSourceConfig;
    }

    private static StrategyConfig getStrategyConfig() {
        List<TableFill> tableFillList = new ArrayList<TableFill>();
        //如 每张表都有一个创建时间、修改时间
        //而且这基本上就是通用的了，新增时，创建时间和修改时间同时修改
        //修改时，修改时间会修改，
        //虽然像Mysql数据库有自动更新几只，但像ORACLE的数据库就没有了，
        //使用公共字段填充功能，就可以实现，自动按场景更新了。
        //如下是配置
        TableFill createField = new TableFill("create_time", FieldFill.INSERT);
        TableFill modifiedField = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        tableFillList.add(createField);
        tableFillList.add(modifiedField);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTableFillList(tableFillList);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        // 设置逻辑删除键
        strategy.setLogicDeleteFieldName("deleted");
        // 指定生成的bean的数据库表名
        strategy.setInclude(table_lists);
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setRestControllerStyle(true);
        // 生成实体时，生成字段注解
        strategy.setEntityTableFieldAnnotationEnable(true);
        return strategy;
    }

    private static PackageConfig getPackageConfig() {
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parent);
        pc.setModuleName(moduleName);
        pc.setEntity("pojo.entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");
        return pc;
    }

    private static TemplateConfig getTemplateConfig() {
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        return tc;
    }

    private static GlobalConfig getGlobalConfig() {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir);
        gc.setAuthor(author);
        gc.setOpen(false);
        //AUTO自增, NONE未设置, INPUT用户输入ID, ID_WORKER全局唯一ID, UUID, ID_WORKER_STR;，ID_Worker是雪花算法生成的主键
        gc.setIdType(IdType.ASSIGN_ID);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setControllerName("%sController");
        // 是否覆盖旧文件
        gc.setFileOverride(true);
        // 是否开启 swagger2
        gc.setSwagger2(true);
        return gc;
    }

    private static InjectionConfig getInjectionConfig() {
        // 自定义注入配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("parentPackage", parent);
                this.setMap(map);
            }
        };
        //TODO 只覆盖 ENTITY 文件
        cfg.setFileCreate((configBuilder, fileType, filePath) -> {
            // 判断自定义文件夹是否需要创建
            if (fileType == FileType.ENTITY) {
                // 已经生成 ENTITY 文件判断存在，不想重新生成返回 false
                return true;
            }
            // 允许生成模板文件
            return !new File(filePath).exists();
        });
        //TODO 自定义 DTO VO 等生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/templates/dto.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String path = outputDir + "/" + parent.replaceAll("\\.", "/") + "/pojo/dto/";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                return path + tableInfo.getEntityName() + "DTO.java";
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

}
