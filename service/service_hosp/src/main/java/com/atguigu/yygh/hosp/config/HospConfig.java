package com.atguigu.yygh.hosp.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注解@Configuration表明是配置类
 * 注释@MapperScan("com.atguigu.yygh.hosp.mapper")可以加到启动类，也可以加到配置文件上
 * @author Li
 */
@Configuration
@MapperScan("com.atguigu.yygh.hosp.mapper")
public class HospConfig {
    /**
     *注解 @Bean 给容器中添加组件。以方法名作为组件的id。返回类型就是组件类型。返回的值，就是组件在容器中的实例
     * 默认单实例
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
