package com.jw.shopping.controller;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.jw.shopping.typehandler.BoardTypeHandler;
import com.jw.shopping.typehandler.LocalDateTypeHandler;
import com.jw.shopping.typehandler.RoleTypeHandler;
import com.jw.shopping.typehandler.SexTypeHandler;
import com.jw.shopping.util.Command;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.jw.shopping")
@MapperScan("com.jw.shopping.dao")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
    	 HikariConfig config = new HikariConfig();
         config.setDriverClassName("com.mysql.cj.jdbc.Driver");
         config.setJdbcUrl("jdbc:mysql://localhost:3306/shopping?serverTimezone=Asia/Seoul");
         config.setUsername("root");
         config.setPassword("1234");
         
         // 커넥션 풀 설정 (필요에 따라 조정)
         config.setMaximumPoolSize(10); // 최대 커넥션 수
         config.setMinimumIdle(5); // 최소 유휴 커넥션 수
         config.setIdleTimeout(30000); // 유휴 커넥션 타임아웃
         config.setConnectionTimeout(30000); // 커넥션 타임아웃

         return new HikariDataSource(config);
     }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/jw/shopping/dao/mapper/*.xml"));
        
        // 타입 핸들러 등록
        factoryBean.setTypeHandlers(new TypeHandler[]{
            new LocalDateTypeHandler(),
            new SexTypeHandler(),
            new RoleTypeHandler(),
            new BoardTypeHandler()
        });
        
        return factoryBean.getObject();
    }

    @Bean
    public SqlSession sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }
    
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(10485760); // 최대 파일 크기 (10MB)
        resolver.setMaxInMemorySize(1024); // 메모리 내 최대 크기 (1KB)
        return resolver;
    }
    
    @Bean
    public Map<String, Command> commands(
            @Qualifier("loginCommand") Command loginCommand,
            @Qualifier("signupCommand") Command signupCommand,
            @Qualifier("addProductCommand") Command addProductCommand,
            @Qualifier("getProductsCommand") Command getProductsCommand,
            @Qualifier("detailProductCommand") Command detailProductCommand,
            @Qualifier("deleteProductCommand") Command deleteProductCommand,
            @Qualifier("updateProductCommand") Command updateProductCommand) {
        Map<String, Command> commands = new HashMap<String, Command>();
        commands.put("loginCommand", loginCommand);
        commands.put("signupCommand", signupCommand);
        commands.put("addProductCommand", addProductCommand);
        commands.put("getProductsCommand", getProductsCommand);
        commands.put("detailProductCommand", detailProductCommand);
        commands.put("deleteProductCommand", deleteProductCommand);
        commands.put("updateProductCommand", updateProductCommand);
        return commands;
    }
    
    @Bean
    public Map<String, Command> boardCommands(
            @Qualifier("listCommand") Command listCommand,
            @Qualifier("contentCommand") Command contentCommand,
            @Qualifier("deleteCommand") Command deleteCommand,
            @Qualifier("modifyCommand") Command modifyCommand,
            @Qualifier("replyViewCommand") Command replyViewCommand,
            @Qualifier("replyCommand") Command replyCommand,
            @Qualifier("writeCommand") Command writeCommand) {
        Map<String, Command> commands = new HashMap<String, Command>();
        commands.put("listCommand", listCommand);
        commands.put("contentCommand", contentCommand);
        commands.put("deleteCommand", deleteCommand);
        commands.put("modifyCommand", modifyCommand);
        commands.put("replyViewCommand", replyViewCommand);
        commands.put("replyCommand", replyCommand);
        commands.put("writeCommand", writeCommand);
        return commands;
    }
}
