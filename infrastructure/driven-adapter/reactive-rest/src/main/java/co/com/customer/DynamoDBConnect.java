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
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.xspec.S;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class DynamoDBConnect implements Database {

    DynamoDB dynamoDB = dynamoDB(dynamoDBClient());
    Table credentials = dynamoDB.getTable("dev-dynamo-customer");


    @Override
    public Mono<Object> evaluate(CustomerInfo customerInfo, String operation){

        switch (operation){
            case "getInfo" : return Mono.just(getInfo(customerInfo));
            case "add" : return Mono.just(addCustomer(customerInfo));
            case "delete": return Mono.just(deleteCustomer(customerInfo));
            case "update": return  Mono.just(updateCustomer(customerInfo));

            default: return Mono.just(getInfo(customerInfo));
        }


    }

    private Object getInfo(CustomerInfo customerInfo) {

        GetItemSpec spec = new GetItemSpec().withPrimaryKey("id",customerInfo.getId(),"docType", customerInfo.getDocType());
        try {
            Item outcome = credentials.getItem(spec);
            if (outcome != null) {
                Map<String, Object> info =outcome.asMap();
                return info;
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
        infomAp.put("nameCustomer",customerInfo.getName());
        infomAp.put("lastname",customerInfo.getLastname());
        infomAp.put("country",customerInfo.getCountry());
        infomAp.put("birth",customerInfo.getBirth());
        infomAp.put("typeBlood",customerInfo.getTypeBlood());
        infomAp.put("state",customerInfo.getState());
        infomAp.put("street",customerInfo.getStreet());

        PutItemOutcome outcome = credentials.putItem(new Item()
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
            DeleteItemOutcome outcome = credentials.deleteItem(spec);

            if (outcome.getDeleteItemResult() != null) {
                infomap.clear();
                infomap.put("stateDelete", "Success");
                infomap.put("infoDeleted",customerInfo);
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

    private Object updateCustomer(CustomerInfo customerInfo){
        String udateQuery = "set ";
        ValueMap valueMap = new ValueMap();
        Map<String,Object> map = new HashMap<>();

        final HashMap<String,Object> infomap = new HashMap<>();
        UpdateItemSpec update = new UpdateItemSpec().withPrimaryKey("id", customerInfo.getId(), "docType", customerInfo.getDocType());
        if(customerInfo.getName() != null){
           udateQuery += ("info.nameCustomer = :name ");
           valueMap.withString(":name",customerInfo.getName());
        }

        if(customerInfo.getLastname() != null){
            udateQuery +=(" , info.lastname = :lastName");
            valueMap.withString(":lastName",customerInfo.getLastname());
        }
        if(customerInfo.getBirth() != null){
            udateQuery +=(" , info.birth = :birth");
            valueMap.withString(":birth",customerInfo.getBirth());
        }
        if(customerInfo.getCountry() != null){
            udateQuery +=(" , info.country = :country");
            valueMap.withString(":country",customerInfo.getCountry());
        }
        if(customerInfo.getState() != null){
            udateQuery +=(" , info.state = :state");
            valueMap.withString(":state",customerInfo.getState());
        }
        if(customerInfo.getTypeBlood() != null){
            udateQuery +=(" , info.typeBlood = :typeBlood");
            valueMap.withString(":typeBlood",customerInfo.getTypeBlood());
        }
        if(customerInfo.getStreet() != null){
            udateQuery +=(" , info.street = :street");
            valueMap.withString(":street",customerInfo.getStreet());
        }


        update.withUpdateExpression(udateQuery).withValueMap(valueMap)
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try{
            UpdateItemOutcome outcome = credentials.updateItem(update);
            if(outcome != null)
                infomap.put("stateUpdate", "Success");

        }catch (Exception e){
            throw new NullPointerException(e.getMessage());
        }

      return  infomap;
    }

    private static AmazonDynamoDB   dynamoDBClient(){
        return AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://localhost:8000/", "ap-southeast-2"))
                .build();
    }

    private static  DynamoDB dynamoDB (AmazonDynamoDB dynamoDBClient){ return new DynamoDB(dynamoDBClient);
    }


}
