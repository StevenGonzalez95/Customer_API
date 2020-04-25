package co.com.api.usecase;

import co.com.api.usecase.detail.CustomerInfo;
import co.com.customer.DynamoDBConnect;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class CustomerUseCase {

    private Database database;

    public Mono<Object> transaction(CustomerInfo info, String operation){
        return database.evaluate(info,operation);

    }




}
