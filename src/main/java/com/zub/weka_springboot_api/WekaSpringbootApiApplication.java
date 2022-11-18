package com.zub.weka_springboot_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.zub.weka_springboot_api.repositories.ContenidoRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.*;

import java.io.File;

@SpringBootApplication
public class WekaSpringbootApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WekaSpringbootApiApplication.class, args);
    }

    private static final Log logger = LogFactory.getLog(WekaSpringbootApiApplication.class);
    @Autowired
    private ContenidoRepository contenidoRepository;


    @Bean
    public void RecommendationSystem() throws Exception{

        // Creacion del data set csv
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(
                objectMapper.writeValueAsBytes(contenidoRepository.findDescribe(Pageable.unpaged()))
        );

        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = jsonNode.elements().next();
        firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);});

        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.writerFor(JsonNode.class)
                .with(csvSchema)
                .writeValue(new File("src/main/resources/data.csv"),jsonNode);

        ApplyWeka.csvToArff("src/main/resources/data.csv","src/main/resources/data.arff");

    }
}
