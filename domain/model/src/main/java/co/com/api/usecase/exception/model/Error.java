package co.com.api.usecase.exception.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;


@Data
@Setter
public class Error   {

    private String detail = null;
    private String timestamp = null;
    private String title = null;
    private int status;

}

