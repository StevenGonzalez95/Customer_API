package co.com.api.defaults;


import co.com.api.usecase.CustomerUseCase;
import co.com.api.usecase.Database;
import co.com.customer.DynamoDBConnect;
import lombok.extern.java.Log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.net.ssl.SSLException;

import javax.xml.bind.JAXBException;

@Log
@Configuration
public class DefaultBeansConfig {


    @Bean
    CustomerUseCase customerUseCase(){ return  new CustomerUseCase(database());}

    @Bean
    Database database(){
        return new DynamoDBConnect();
    }


}
