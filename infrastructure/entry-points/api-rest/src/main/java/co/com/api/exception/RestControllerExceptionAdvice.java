package co.com.api.exception;


import co.com.api.usecase.exception.CustomerException;
import co.com.api.usecase.exception.model.Error;
import co.com.api.usecase.exception.model.ErrorRS;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@ControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionAdvice {


    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<Object> handle(CustomerException e)
    {
        Error error = new Error();
        ErrorRS errorRS = new ErrorRS();
        List<Error> errors = new ArrayList<>();
        error.setDetail(e.getStatus().getMessage());
        error.setStatus(Integer.parseInt(e.getStatus().getCode()));
        error.setTitle(e.getStatus().getTittle());
        errors.add(error);
        errorRS.setErrors(errors);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorRS);

    }



    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handle(NullPointerException e)
    {
        Error error = new Error();
        ErrorRS errorRS = new ErrorRS();
        List<Error> errors = new ArrayList<>();
        error.setDetail(e.getMessage().trim());
        error.setStatus(500);
        errors.add(error);
        errorRS.setErrors(errors);


        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorRS);
    }



}


