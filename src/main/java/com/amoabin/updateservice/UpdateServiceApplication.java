package com.amoabin.updateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@ComponentScan(basePackages = {"com.amoabin.updateservice.service", "com.amoabin.updateservice.rest", "com.amoabin.updateservice.remote", "com.amoabin.updateservice.kafka", "com.amoabin.updateservice.config"})
public class UpdateServiceApplication {
    public static void main
            (String[] args) {
                SpringApplication.run(UpdateServiceApplication.class, args);

    }
    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQL95Dialect"); // Use the appropriate dialect version
        return adapter;
    }
}
