package com.zub.weka_springboot_api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.zub.weka_springboot_api.ApplyWeka;
import com.zub.weka_springboot_api.models.Contenido;
import com.zub.weka_springboot_api.models.RepContenido;
import com.zub.weka_springboot_api.repositories.ContenidoRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import weka.core.DenseInstance;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/contenido")
public class ContenidoController {
    @Autowired
    private ContenidoRepository contenidoRepository;

    @GetMapping("/findAll")
    public List<Contenido> findAll(Pageable pageable){
        return contenidoRepository.findAll(pageable);
    }

    @GetMapping("/findDescribe")
    public List<RepContenido> findDescribe(Pageable pageable){
        return contenidoRepository.findDescribe(pageable);
    }

    @PostMapping("/recommendations")
    public List<RepContenido> eval(@RequestBody List<RepContenido> repContenido) throws Exception {

        ApplyWeka.RequestToCSV(repContenido.toArray(),"src/main/resources/test.csv");

        ApplyWeka.csvToArff("src/main/resources/test.csv","src/main/resources/test.arff");

        ApplyWeka.Recommendations("src/main/resources/test.arff","src/main/resources/data.arff");

        return repContenido;
    }
}
