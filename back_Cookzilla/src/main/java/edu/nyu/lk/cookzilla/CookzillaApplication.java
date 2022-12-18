package edu.nyu.lk.cookzilla;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@MapperScan(basePackages = "edu.nyu.lk.cookzilla.dao")
@SpringBootApplication
//@ComponentScan(basePackages = "edu.nyu.lk.cookzilla.dao")
public class CookzillaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CookzillaApplication.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {

        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();

        c.setIgnoreUnresolvablePlaceholders(true);

        return c;
    }

}
