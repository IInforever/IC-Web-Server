/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
public class MybatisConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // Block SQL Attack
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // Pagination plugin
        PaginationInnerInterceptor paginationInnerInterceptor;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            log.info("DbType: mariadb");
            paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MARIADB);
        } catch (ClassNotFoundException ignore) {
            log.info("DbType: MySQL");
            paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        }
        paginationInnerInterceptor.setMaxLimit(1000L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

}
