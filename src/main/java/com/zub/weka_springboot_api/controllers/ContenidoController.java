package com.zub.weka_springboot_api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.zub.weka_springboot_api.ApplyWeka;
import com.zub.weka_springboot_api.models.Contenido;
import com.zub.weka_springboot_api.models.ContenidoInstancia;
import com.zub.weka_springboot_api.models.RepContenido;
import com.zub.weka_springboot_api.repositories.ContenidoRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/contenido")
public class ContenidoController {

    private MongoTemplate mongoTemplate;
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

        //ApplyWeka.RequestToCSV(repContenido.toArray(),"src/main/resources/test.csv");

        //ApplyWeka.csvToArff("src/main/resources/test.csv","src/main/resources/test.arff");

        ApplyWeka.Recommendations("src/main/resources/test.arff","src/main/resources/data.arff");

        return repContenido;
    }

    @GetMapping("/get-content")
    public ArrayList<ContenidoInstancia> GetContent(Pageable pageable) throws Exception {

        Instances dataset = ApplyWeka.LoadDatasetArff("src/main/resources/datasetWithClasses.arff");

        ArrayList<ContenidoInstancia> instancias = new ArrayList<>();
        Random rnd = new Random();
        Random rand = dataset.getRandomNumberGenerator(rnd.nextInt());

        // Retornar contenido aleatorio
        for (int i=0;i<40;i++){
            int numInstance = rand.nextInt(dataset.numInstances());
            Instance inst = dataset.instance(numInstance);

            String id = inst.attribute(1).value((int) inst.value(0));
            //System.out.println(id);
            Contenido contenido = contenidoRepository.findByID(id);

            ContenidoInstancia temp = new ContenidoInstancia();
            //temp.setNumInstancia(numInstance);
            temp.setNumInstancia(numInstance);
            temp.setContenido(contenido);

            instancias.add(temp);
        }

        return instancias ;
    }

    @CrossOrigin
    @PostMapping("/get-recommendations")
    public List<ContenidoInstancia> GetRecommendations(@RequestBody List<ContenidoInstancia> testList) throws Exception {

        /*
        System.out.println(testList);
        ConverterUtils.DataSource source = new ConverterUtils.DataSource("src/main/resources/datasetWithClasses.arff");
        Instances datasetOriginal = source.getDataSet();
        datasetOriginal.setClassIndex(datasetOriginal.numAttributes() - 1);
        ApplyWeka.SaveInstanceToArff(datasetOriginal,"src/main/resources/datasetWithClassesTest.arff");
        */
        ArrayList<String> contenidoIDs = new ArrayList<>();

        for(ContenidoInstancia inst:testList){
            ArrayList<String> res = ApplyWeka.KDTree("src/main/resources/datasetWithClassesTest.arff",inst.getNumInstancia(),3);
            contenidoIDs.removeAll(res);
            contenidoIDs.addAll(res);
        }

        List<ContenidoInstancia> recomendaciones = new ArrayList<>();
        //List<Contenido> contenidos = new ArrayList<>();

        for (String id:contenidoIDs) {
            Contenido temp = contenidoRepository.findByID(id);
            ContenidoInstancia recomendacion = new ContenidoInstancia();
            recomendacion.setContenido(temp);
            recomendaciones.add(recomendacion);
        }

        return recomendaciones;
    }
}
