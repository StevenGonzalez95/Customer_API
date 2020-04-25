package co.com.api.customer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerRQDataParam {

    @JsonProperty("id")
    private String id;
    @JsonProperty("docType")
    private String docType;
    @JsonProperty("name")
    private String name;
    @JsonProperty("lastName")
    private String lastname;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("typeBlood")
    private String typeBlood;
    @JsonProperty("street")
    private String street;
    @JsonProperty("country")
    private String country;
    @JsonProperty("birth")
    private String birth;
    @JsonProperty("state")
    private String state;

}
