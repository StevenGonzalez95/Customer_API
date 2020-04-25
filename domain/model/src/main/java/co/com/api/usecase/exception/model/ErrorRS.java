package co.com.api.usecase.exception.model;


import java.util.ArrayList;
import java.util.List;


public class ErrorRS   {

    private List<Error> errors = null;
    public ErrorRS(){
        this.errors = new ArrayList<Error>();
    }

    public ErrorRS addErrorsItem(Error errorsItem) {
        this.errors.add(errorsItem);
        return this;
    }

    public List<Error> getErrors() {
        return errors;
    }
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

}


