package com.shop.cust.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.shop.cust.mapper")
//@MapperScan({ "com.shop.cust.mapper", "com.snfq.base.common.mapper" })
public class MybatisConfig {
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
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        /** 设置mybatis configuration 扫描路径 */
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
        /** 添加mapper 扫描路径 */
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + MAPPER_PATH;
        sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver
            .getResources(packageSearchPath));
        /** 设置datasource */
        sqlSessionFactoryBean.setDataSource(dataSource);
        /** 设置typeAlias 包扫描路径 */
        sqlSessionFactoryBean.setTypeAliasesPackage(TYPE_ALIAS_PACKAGE);
        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    //    @Bean
    //    public PlatformTransactionManager annotationDrivenTransactionManager() {
    //        return new DataSourceTransactionManager(dataSource);
    //    }
}
