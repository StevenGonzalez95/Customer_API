package co.com.api.usecase;

import co.com.api.usecase.detail.CustomerInfo;
import reactor.core.publisher.Mono;

public interface Database {


    Mono<Object> evaluate(CustomerInfo customerInfo, String operation);
}
