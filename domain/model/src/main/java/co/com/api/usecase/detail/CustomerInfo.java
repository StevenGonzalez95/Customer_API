package co.com.api.usecase.detail;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
public class CustomerInfo {

    private String name;
    private String lastname;
    private String id;
    private String docType;
    private String phone;
    private String typeBlood;
    private String street;
    private String country;
    private String birth;
    private String state;


}
