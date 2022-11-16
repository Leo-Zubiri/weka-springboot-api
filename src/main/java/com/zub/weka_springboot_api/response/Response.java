package com.zub.weka_springboot_api.response;

import lombok.Data;

@Data
public class Response {
    String id;
    String name;
    String data;

    public Response(String name,String data){
        this.name = name;
        this.data = data;
    }
}
