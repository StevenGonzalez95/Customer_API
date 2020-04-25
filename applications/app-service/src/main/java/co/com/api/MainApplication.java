package co.com.api;

import co.com.api.customer.controller.DetailCustomerController;
import co.com.api.defaults.DefaultBeansConfig;

import co.com.api.exception.RestControllerExceptionAdvice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@EnableAutoConfiguration()
@ComponentScan(basePackageClasses = {
        DetailCustomerController.class,
        RestControllerExceptionAdvice.class,
        DefaultBeansConfig.class
})
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}