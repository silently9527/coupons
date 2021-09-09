package grape.plugins.system.tools.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * mybatis plus代码生成
 * @author starBlues
 * @version 1.0
 * @since 2020-08-07
 */
public class CodeGenerator {


    /**
     * 启动名称
     */
    private String driverName;

    /**
     * 数据库 url
     */
    private String dbUrl;

    /**
     * 数据库用户名
     */
    private String dbUsername;

    /**
     * 数据库密码
     */
    private String dbPassword;

    /**
     * 数据库类型
     */
    private DbType dbType;

    /**
     * 作者名
     */
    private String author;

    /**
     * 代码输出路径
     */
    private String outputDir;

    /**
     * 构造器
     * @param driverName 驱动名称
     * @param dbUrl 数据库 url
     * @param dbUsername 数据库用户名
     * @param dbPassword 数据库密码
     * @param author 作者名
     * @param outputDir 代码输出路径
     */
    public CodeGenerator(String driverName, String dbUrl, String dbUsername, String dbPassword, DbType dbType,
                         String author, String outputDir) {
        this.driverName = driverName;
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.dbType = dbType;
        this.author = author;
        this.outputDir = outputDir;
    }

    /**
     * 通过表名生成代码
     * @param packageName 包名
     * @param isOpen 是否打开文件夹
     * @param tableNames 表名
     */
    public void generateByTables(String packageName, boolean isOpen, String... tableNames) {
        generateByTables(true, packageName, isOpen, tableNames);
    }

    /**
     * 通过表名生成代码
     * @param packageName 包名
     * @param isOpen 是否打开文件夹
     */
    public void generateAll(String packageName, boolean isOpen) {
        generateByTables(true, packageName, isOpen);
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, boolean isOpen, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(dbType)
                .setUrl(dbUrl)
                .setUsername(dbUsername)
                .setPassword(dbPassword)
                .setDriverName(driverName);
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setEntityBuilderModel(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setRestControllerStyle(true);
        if(tableNames != null){
            strategyConfig.setInclude(tableNames);
        }
        config.setActiveRecord(false)
                .setAuthor(author)
                .setOutputDir(outputDir)
                .setFileOverride(true)
                .setBaseResultMap(true)
                .setIdType(IdType.ASSIGN_UUID)
                .setEnableCache(false)
                .setBaseColumnList(true)
                .setOpen(isOpen);
        if (serviceNameStartWithI) {
            config.setServiceName("%sService")
                    .setServiceImplName("%sServiceImpl");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                ).execute();
    }


}
