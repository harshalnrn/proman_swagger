package com.upgrad.proman.api;

//import com.upgrad.proman.service.ServiceConfiguration;
import com.upgrad.proman.service.ServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
/*@ComponentScan("com.upgrad.proman.service")
@EntityScan("com.upgrad.proman.service.entity")*/
@Import(ServiceConfiguration.class)
public class PromanApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PromanApiApplication.class, args);
    }
}