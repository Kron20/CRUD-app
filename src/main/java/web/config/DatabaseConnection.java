package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement

@PropertySource("classpath:prop.properties")

@EnableJpaRepositories("web")

public class DatabaseConnection {
    private static final String DRIVER = "db.driver";
    private static final String PASSWORD = "db.password";
    private static final String URL = "db.url";
    private static final String USERNAME = "db.username";
    private static final String DIALECT = "hibernate.dialect";
    private static final String SHOW_SQL = "hibernate.show_sql";
    private static final String FORMAT_SQL = "hibernate.format_sql";
    private static final String PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
    private static final String HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

    private Environment env;

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(getJpaVendorAdapter());
        factoryBean.setDataSource(getDataSource());
        factoryBean.setPersistenceUnitName("myJpaPersistenceUnit");
        factoryBean.setPackagesToScan(env.getRequiredProperty(PACKAGES_TO_SCAN));
        factoryBean.setJpaProperties(getHibernateProperties());
        return factoryBean;
    }

    @Bean
    public JpaVendorAdapter getJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }


    @Bean(name = "transactionManager")
    public PlatformTransactionManager getTransactionManager() {
        return new JpaTransactionManager(getEntityManagerFactoryBean().getObject());
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty(DRIVER));
        dataSource.setUrl(env.getRequiredProperty(URL));
        dataSource.setUsername(env.getRequiredProperty(USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PASSWORD));
        return dataSource;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(DIALECT, env.getRequiredProperty(DIALECT));
        properties.put(SHOW_SQL, env.getRequiredProperty(SHOW_SQL));
        properties.put(FORMAT_SQL, env.getRequiredProperty(FORMAT_SQL));
        properties.put(HBM2DDL_AUTO, env.getRequiredProperty(HBM2DDL_AUTO));

        return properties;
    }
}
