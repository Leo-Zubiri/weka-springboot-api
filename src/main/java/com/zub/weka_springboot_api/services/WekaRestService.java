package com.zub.weka_springboot_api.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.zub.weka_springboot_api.response.Response;


import java.util.ArrayList;


@RestController
@RequestMapping(path="/weka", produces="application/json")
@CrossOrigin(origins="*")

public class WekaRestService {
    // Métodos HTTP

    @GetMapping("/verify-connection1")
    public ArrayList<Response> getAll(){
        ArrayList<Response> arr = new ArrayList<>();

        arr.add(new Response("TestAll","Passed"));
        return arr;
    }

    @GetMapping("/verify-connection2")
    public ResponseEntity<Response> get(){
        Response data = new Response("Test","Toy vivo perro Succesfully :)");

        HttpHeaders headers = new HttpHeaders();
        ResponseEntity entity = new ResponseEntity<>(data,headers,HttpStatus.OK);
        return entity;
    }
}
