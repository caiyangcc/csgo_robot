package com.softisland;



import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
@Configuration
public class DruidDBConfig2 {
	  
      
	    @Value("${spring.datasource.slaveurl}")  
	    private String dbUrl;  
	      
	    @Value("${spring.datasource.slaveusername}")  
	    private String username;  
	      
	    @Value("${spring.datasource.slavepassword}")  
	    private String password;  
	      
	    @Value("${spring.datasource.driverClassName}")  
	    private String driverClassName;  
	      
	    @Value("${spring.datasource.initialSize}")  
	    private int initialSize;  
	      
	    @Value("${spring.datasource.minIdle}")  
	    private int minIdle;  
	      
	    @Value("${spring.datasource.maxActive}")  
	    private int maxActive;  
	      
	    @Value("${spring.datasource.maxWait}")  
	    private int maxWait;  
	      
	    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")  
	    private int timeBetweenEvictionRunsMillis;  
	      
	    @Value("${spring.datasource.minEvictableIdleTimeMillis}")  
	    private int minEvictableIdleTimeMillis;  
	      
	    @Value("${spring.datasource.validationQuery}")  
	    private String validationQuery;  
	      
	    @Value("${spring.datasource.testWhileIdle}")  
	    private boolean testWhileIdle;  
	      
	    @Value("${spring.datasource.testOnBorrow}")  
	    private boolean testOnBorrow;  
	      
	    @Value("${spring.datasource.testOnReturn}")  
	    private boolean testOnReturn;  
	      
	    @Value("${spring.datasource.maxOpenPreparedStatements}")  
	    private int maxOpenPreparedStatements;  
 
	    
	    @Value("${spring.datasource.removeAbandoned}")
	    private boolean removeAbandoned;
	    
	    @Value("${spring.datasource.removeAbandonedTimeout}")
	    private int removeAbandonedTimeout;
	    
	    @Value("${spring.datasource.logAbandoned}")
	    private boolean logAbandoned;
	      
	    @Bean(name = "DataSource2")     //声明其为Bean实例   //在同样的DataSource中，首先使用被标注的DataSource  
	    public DataSource dataSource(){  
	        DruidDataSource datasource = new DruidDataSource();  
	          
	        datasource.setUrl(this.dbUrl);  
	        datasource.setUsername(username);  
	        datasource.setPassword(password);  
	        datasource.setDriverClassName(driverClassName);  
	          
	        //configuration  
	        datasource.setInitialSize(initialSize);  
	        datasource.setMinIdle(minIdle);  
	        datasource.setMaxActive(maxActive);  
	        datasource.setMaxWait(maxWait);  
	        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
	        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
	        datasource.setValidationQuery(validationQuery);  
	        datasource.setTestWhileIdle(testWhileIdle);  
	        datasource.setTestOnBorrow(testOnBorrow);  
	        datasource.setTestOnReturn(testOnReturn);  
	        datasource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
	        datasource.setRemoveAbandoned(removeAbandoned);
	        datasource.setRemoveAbandonedTimeout(removeAbandonedTimeout);    
	        return datasource;  
	    }
	    
	    @Bean(name = "SqlSessionFactory2")
		public SqlSessionFactory sqlSessionFactory(@Qualifier("DataSource2") DataSource dataSource) throws Exception {
			SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
			bean.setDataSource(dataSource);
			bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/mybatis-config.xml"));
			bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml"));
			return bean.getObject();
		}

		@Bean(name = "TransactionManager2")
		public DataSourceTransactionManager testTransactionManager(@Qualifier("DataSource2") DataSource dataSource) {
			return new DataSourceTransactionManager(dataSource);
		}

		@Bean(name = "sqlSessionTemplate2")
		public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SqlSessionFactory2") SqlSessionFactory sqlSessionFactory) throws Exception {
			return new SqlSessionTemplate(sqlSessionFactory);
		}
}
