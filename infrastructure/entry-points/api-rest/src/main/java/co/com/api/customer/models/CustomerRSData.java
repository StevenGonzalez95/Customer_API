package co.com.api.customer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

@Validated
@Data

public class CustomerRSData {

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("uniqueid")
    private String uniqueid = null;

    private HashMap<String,Object> param;



}
