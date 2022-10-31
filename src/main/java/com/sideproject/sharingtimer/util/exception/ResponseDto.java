package com.sideproject.sharingtimer.util.exception;

import lombok.*;

@Data
public class ResponseDto {
    private String msg;
    private Object data;
    private CustomException errorCode;


    public ResponseDto(){}

    public ResponseDto(String msg){
        this.msg = msg;
    }

    public ResponseDto(String msg, Object data){
        this.msg = msg;
        this.data = data;
    }

    public ResponseDto(CustomException errorCode){
        this.errorCode = errorCode;
    }

}