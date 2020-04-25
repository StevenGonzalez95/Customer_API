package co.com.api.usecase.exception;



import co.com.api.usecase.StatusResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CustomerException extends RuntimeException {

    private final StatusResponse status;

}
