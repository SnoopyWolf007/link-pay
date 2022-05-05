package com.link.pay.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author jcm
 */

@Configuration
@MapperScan(basePackages = "com.link.pay.core.dao", sqlSessionFactoryRef = "linkSqlSessionFactory")
public class DatasourceConfig {

    @Bean(name = "linkDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.link")
    public DataSource geLinkStoreDataSource() {
        return new HikariDataSource();
    }

    /**
     * {@link org.apache.ibatis.session.Configuration}每个数据源需要配置一份，不然导致多数据源不可用
     */
    @Bean(name = "linkGlobalConfig")
    @ConfigurationProperties(prefix = "mybatis.configuration.link")
    public org.apache.ibatis.session.Configuration getLinkGlobalConfig() {
        return new org.apache.ibatis.session.Configuration();
    }

    @Bean(name = "linkTransactionManager")
    @Primary
    public DataSourceTransactionManager linkTransactionManager(@Qualifier("linkDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "linkSqlSessionFactory")
    @Primary
    public SqlSessionFactory linkSqlSessionFactory(@Qualifier("linkDataSource") DataSource dataSource) throws Exception {
        final MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);


        //设置mybatis-plus分页插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        sessionFactoryBean.setPlugins(interceptor);

        //自动注入
        GlobalConfig defaults = GlobalConfigUtils.defaults();
        defaults.setMetaObjectHandler(new IMetaObjectHandlerConfig());
        sessionFactoryBean.setGlobalConfig(defaults);

        return sessionFactoryBean.getObject();
    }

}
