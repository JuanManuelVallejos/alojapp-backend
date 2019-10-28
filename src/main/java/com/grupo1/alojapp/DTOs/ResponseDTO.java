package com.grupo1.alojapp.DTOs;

public class ResponseDTO extends ResponseHttp {

    private String errorMessage;

    public ResponseDTO(){};

    public ResponseDTO(String errorMessage){
        this.setErrorMessage(errorMessage);
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
