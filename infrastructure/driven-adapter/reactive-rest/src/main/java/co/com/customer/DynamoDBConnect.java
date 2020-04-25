package co.com.customer;

import co.com.api.usecase.Database;
import co.com.api.usecase.StatusResponse;
import co.com.api.usecase.detail.CustomerInfo;
import co.com.api.usecase.exception.CustomerException;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.xspec.S;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class DynamoDBConnect implements Database {

    DynamoDB dynamoDB = dynamoDB(dynamoDBClient());
    Table table = dynamoDB.getTable("dev-dynamo-customer");


    @Override
    public Mono<Object> evaluate(CustomerInfo customerInfo, String operation){

        switch (operation){
            case "getInfo" : return Mono.just(getInfo(customerInfo));
            case "add" : return Mono.just(addCustomer(customerInfo));
            case "delete": return Mono.just(deleteCustomer(customerInfo));

            default: return Mono.just(getInfo(customerInfo));
        }


    }

    private CustomerInfo getInfo(CustomerInfo customerInfo) {

        GetItemSpec spec = new GetItemSpec().withPrimaryKey("id",customerInfo.getId(),"docType", customerInfo.getDocType());
        try {
            Item outcome = table.getItem(spec);
            if (outcome != null) {
                Map<String, Object> info =outcome.asMap();
                return customerInfo;
            } else {
                StatusResponse statusResponse = new StatusResponse();
                statusResponse.setTittle("Client not found");
                statusResponse.setMessage("La información no coincide con la base de datos");
                statusResponse.setCode("409");
                throw new CustomerException(statusResponse);
            }
        }catch (Exception e){
            StatusResponse statusResponse = new StatusResponse();
            statusResponse.setTittle("Client not found");
            statusResponse.setMessage("La información no coincide con la base de datos");
            statusResponse.setCode("500");
            throw new CustomerException(statusResponse);

        }


    }

    private Object addCustomer(CustomerInfo customerInfo){
        Boolean succes = false;
        final Map<String, Object> infomAp = new HashMap<String, Object>();
        infomAp.put("docType",customerInfo.getDocType());
        infomAp.put("id",customerInfo.getId());
        infomAp.put("name",customerInfo.getName());
        infomAp.put("lastname",customerInfo.getLastname());
        infomAp.put("country",customerInfo.getCountry());
        infomAp.put("birth",customerInfo.getBirth());
        infomAp.put("typeBlood",customerInfo.getTypeBlood());
        infomAp.put("state",customerInfo.getState());
        infomAp.put("street",customerInfo.getStreet());

        PutItemOutcome outcome = table.putItem(new Item()
                .withPrimaryKey("id",customerInfo.getId(),"docType",customerInfo.getDocType())
                .withMap("info",infomAp));

        if(outcome.getPutItemResult() != null)
            infomAp.clear();
            infomAp.put("state","Success");

        return infomAp;
    }


    private Object deleteCustomer(CustomerInfo customerInfo) {
        final HashMap<String,Object> infomap = new HashMap<>();

        try {
            DeleteItemSpec spec = new DeleteItemSpec().withPrimaryKey("id", customerInfo.getId(), "docType", customerInfo.getDocType());
            DeleteItemOutcome outcome = table.deleteItem(spec);

            if (outcome.getDeleteItemResult() != null) {
                infomap.clear();
                infomap.put("stateDelete", "Success");
            } else {
                StatusResponse statusResponse = new StatusResponse();
                statusResponse.setCode("010");
                statusResponse.setMessage("El cliente no existe para realizar la operación indicada");
                statusResponse.setTittle("Error consultando cliente");
                throw new CustomerException(statusResponse);
            }
        }catch (Exception e){
            throw new NullPointerException(e.getMessage());
        }


        return infomap;
    }




    private static AmazonDynamoDB   dynamoDBClient(){
        return AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://localhost:8000/", "ap-southeast-2"))
                .build();
    }


    private static  DynamoDB dynamoDB (AmazonDynamoDB dynamoDBClient){ return new DynamoDB(dynamoDBClient);
    }


}
