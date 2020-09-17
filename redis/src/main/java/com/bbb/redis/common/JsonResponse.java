package com.bbb.redis.common;


import com.fasterxml.jackson.annotation.JsonInclude;

public class JsonResponse<T> {
    public static final int SC_SUCCESS = 200;
    public static final int SC_SERVER_ERROR = 500;
    public static final int SC_CLIENT_ERROR = 400;
    public static final String MSG_SUC="success";
    public static final String MSG_ERR="error";

    private int code=SC_SUCCESS;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg=null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data=null;

    public JsonResponse(){

    }
    public int getCode(){
        return this.code;
    }
    public JsonResponse<T> setCode(int code){
        this.code=code;
        return this;
    }
    public String getMsg(){
        return this.msg;
    }

    public JsonResponse<T> setMsg(String msg){
        this.msg=msg;
        return this;
    }
    public T getData(){
        return this.data;
    }
    public JsonResponse<T> setData(T data){
        this.data=data;
        return this;
    }







}
