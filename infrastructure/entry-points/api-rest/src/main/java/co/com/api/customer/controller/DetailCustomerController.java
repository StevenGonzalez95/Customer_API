package co.com.api.customer.controller;



import ch.qos.logback.core.joran.util.beans.BeanUtil;
import co.com.api.customer.models.CustomerRQ;


import co.com.api.customer.models.CustomerRS;
import co.com.api.customer.models.CustomerRSData;
import co.com.api.usecase.CustomerUseCase;
import co.com.api.usecase.detail.CustomerInfo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/pruebasCGC")

public class DetailCustomerController {

    private CustomerUseCase customerUseCase;


    @PostMapping("/customerAdd")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Object>> detailsDepositsAccount(@RequestBody CustomerRQ customerRQ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Inicia una transacción : body customerRQ {} " + mapper.writeValueAsString(customerRQ));

        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(customerRQ.getData().get(0).getParam(),customerInfo);
        return customerUseCase.transaction(customerInfo, "add")
         .map(response -> {
             return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                     .body(responseData(response,customerRQ));
         });
    }

    @DeleteMapping("/customerDelete")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Object>> deleteCustomer(@RequestBody CustomerRQ customerRQ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Inicia una transacción : body customerRQ {} " + mapper.writeValueAsString(customerRQ));

        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(customerRQ.getData().get(0).getParam(),customerInfo);
        return customerUseCase.transaction(customerInfo, "delete")
                .map(response -> {
                    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                            .body(responseData(response,customerRQ));
                });
    }

    @GetMapping("/customerInfo")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Object>> getCustomer(@RequestBody CustomerRQ customerRQ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Inicia una transacción : body customerRQ {} " + mapper.writeValueAsString(customerRQ));

        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(customerRQ.getData().get(0).getParam(),customerInfo);

        return customerUseCase.transaction(customerInfo, "getInfo")
                .map(response -> {
                    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                            .body(responseData(response,customerRQ));
                });
    }

    @PutMapping("/customerUpdate")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Object>> updateCustomer(@RequestBody CustomerRQ customerRQ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Inicia una transacción : body customerRQ {} " + mapper.writeValueAsString(customerRQ));

        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(customerRQ.getData().get(0).getParam(),customerInfo);

        return customerUseCase.transaction(customerInfo, "update")
                .map(response -> {
                    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                            .body(responseData(response,customerRQ));
                });
    }

    public CustomerRS responseData(Object object, CustomerRQ customerRQ) {

        CustomerRS  customerRS= new CustomerRS();
        CustomerRSData customerRSData = new CustomerRSData();

        BeanUtils.copyProperties(customerRQ.getData().get(0),customerRSData);
        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("info",object);
        customerRSData.setParam(map);

        List<CustomerRSData> list = new ArrayList<>();
        list.add(customerRSData);
        customerRS.setData(list);
        return customerRS ;

    }






}
