package com.shop.cust.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@EnableTransactionManagement
@MapperScan("com.shop.cust.mapper")
//@MapperScan({ "com.shop.cust.mapper", "com.snfq.base.common.mapper" })
public class MybatisConfig { //implements TransactionManagementConfigurer {

    /** * mybatis 配置路径 */
    private static String MYBATIS_CONFIG     = "mybatis-config.xml";
    /** * mybatis mapper resource 路径     */
    private static String MAPPER_PATH        = "/mapper/**.xml";
    /** * mybatis 映射实体路径     */
    private static String TYPE_ALIAS_PACKAGE = "com.shop.cust.dao";
    @Autowired
    private DataSource    dataSource;

    /** 
    *创建sqlSessionFactoryBean 实例 
    * 并且设置configtion 如驼峰命名.等等 
    * 设置mapper 映射路径 
    * 设置datasource数据源 
    * @return 
    * @throws Exception 
    */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        /** 设置mybatis configuration 扫描路径 */
        bean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
        /** 添加mapper 扫描路径 */
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + MAPPER_PATH;
        bean.setMapperLocations(resolver.getResources(packageSearchPath));
        /** 设置datasource */
        bean.setDataSource(dataSource);
        /** 设置typeAlias 包扫描路径 */
        bean.setTypeAliasesPackage(TYPE_ALIAS_PACKAGE);

        //        // 分页插件
        //        PageHelper pageHelper = new PageHelper();
        //        Properties properties = new Properties();
        //        properties.setProperty("reasonable", "true");
        //        properties.setProperty("supportMethodsArguments", "true");
        //        properties.setProperty("returnPageInfo", "check");
        //        properties.setProperty("params", "count=countSql");
        //        pageHelper.setProperties(properties);
        //        //添加插件
        //        bean.setPlugins(new Interceptor[]{pageHelper});
        return bean;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
