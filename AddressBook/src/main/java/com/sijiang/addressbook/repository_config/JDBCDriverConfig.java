package com.sijiang.addressbook.repository_config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.sijiang.addressbook.repo.AddressBookRepository;
import com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl;

@Configuration
@ComponentScan({ "com.sijiang.addressbook.repo.impl",
		"com.sijiang.addressbook.dao.impl" })
@PropertySource("classpath:jdbc.properties")
@EnableTransactionManagement(proxyTargetClass = true)
public class JDBCDriverConfig implements TransactionManagementConfigurer {
	private static final JdbcTemplate jdbcTemplate = new JdbcTemplate();
	private static final DriverManagerDataSource dataSource = new DriverManagerDataSource();

	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	
	@Value("${jdbc.url}")
	private String url;
	
	@Value("${jdbc.username}")
	private String username;
	
	@Value("${jdbc.password}")
	private String password;
	
	@Bean(name = "JDBCTemplate")
	public JdbcTemplate getJdbcTemplate() {
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		jdbcTemplate.setDataSource(dataSource);

		return jdbcTemplate;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}
	
    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager();
    }
}
