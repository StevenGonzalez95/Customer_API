package co.com.api.customer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashMap;

@Validated
@Data

public class CustomerRQData {

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("uniqueid")
    private String uniqueid = null;

    @JsonProperty("param")
    private CustomerRQDataParam param = null;

}
