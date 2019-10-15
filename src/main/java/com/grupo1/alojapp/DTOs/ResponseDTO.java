package com.grupo1.alojapp.DTOs;

public class ResponseDTO<T> {

    private T data;
    private String errorMessage;

    public ResponseDTO(){};

    public ResponseDTO(T data){
        this.setData(data);
    }

    public ResponseDTO(String errorMessage){
        this.setErrorMessage(errorMessage);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
