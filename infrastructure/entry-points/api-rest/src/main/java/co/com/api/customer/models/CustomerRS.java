package co.com.api.customer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Data
public class CustomerRS {

    @JsonProperty("data")
    private List<CustomerRSData> data = null;
}
