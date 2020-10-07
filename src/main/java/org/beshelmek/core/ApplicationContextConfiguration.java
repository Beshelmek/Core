package org.beshelmek.core;

import org.beshelmek.core.dao.UserDAO;
import org.beshelmek.core.datasource.SiteDataSource;
import org.hibernate.dialect.MariaDB103Dialect;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableJpaRepositories("org.beshelmek.core.*")
@ComponentScan("org.beshelmek.core.*")
@EntityScan("org.beshelmek.core.*")
@EnableTransactionManagement
public class ApplicationContextConfiguration {
    @Bean
    @Scope("singleton")
    public SiteDataSource siteDataSource()
    {
        return new SiteDataSource();
    }

    @Bean
    @Scope("singleton")
    public UserDAO userDAO()
    {
        return new UserDAO();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(siteDataSource().getDataSource());
        sessionFactory.setPackagesToScan("org.beshelmek.core.*");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("org.beshelmek.core.*");
        factory.setDataSource(siteDataSource().getDataSource());
        return factory;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", MariaDB103Dialect.class.getName());

        return hibernateProperties;
    }
}
