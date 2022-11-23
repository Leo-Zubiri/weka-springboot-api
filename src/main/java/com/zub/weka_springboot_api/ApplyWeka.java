package com.zub.weka_springboot_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Debug;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;
import weka.core.converters.ConverterUtils;
import weka.core.neighboursearch.KDTree;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public abstract class ApplyWeka {
    public static void csvToArff(String filePath,String destinyPath) throws IOException {
        // Conversion a arff format para weka
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(filePath));
        Instances data = loader.getDataSet();

        File fileArrf = new File(destinyPath);

        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(fileArrf);
        saver.writeBatch();
    }

    public static void ArffToCsv(Instances dataset,String fullpath) throws IOException {
        // save CSV
        CSVSaver saver = new CSVSaver();
        saver.setInstances(dataset);//set the dataset we want to convert
        //and save as CSV
        saver.setFile(new File(fullpath));
        saver.writeBatch();
    }

    public static void ApplyingMethod(String arffPath) throws Exception {
        File fileArff = new File(arffPath);
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(fileArff.getAbsolutePath());
        Instances instances = source.getDataSet();
        instances.setClassIndex(instances.numAttributes()-1);
    }

    public static void Recommendations(String testPath,String datasetPath) throws Exception {

        // Cargar Dataset y Peticion
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(datasetPath);
        Instances dataset = source.getDataSet();
        //dataset.setClassIndex(dataset.numAttributes()-1);
        //System.out.println(dataset.toString());

        source = new ConverterUtils.DataSource(testPath);
        Instances test = source.getDataSet();

        //dataset.setClassIndex(dataset.numAttributes()-1);
        //test.setClassIndex(test.numAttributes()-1);

        // ----------------------------------------------------------------------------------------------


        /*
        // Generar a que cluster pertenece cada instancia en el dataset
        Instances res = IdentifyClusterAsClasses(dataset,test);

        // Save clustering results
        SaveInstanceToArff(res,"src/main/resources/datasetWithClasses.arff");
        */

        KDTree("src/main/resources/datasetWithClasses.arff", 8,3);
    }

    public static Instances LoadDatasetArff(String datasetPath) throws Exception {
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(datasetPath);
        Instances dataset = source.getDataSet();
        return dataset;
    }

    public static void SaveInstanceToArff(Instances instance, String fullStringPath) throws Exception {
        String outputFilename = fullStringPath;
        ConverterUtils.DataSink.write(outputFilename, instance);
    }

    public static void TestingClustering(Instances dataset,Instances test) throws Exception {

        // generate data for clusterer (w/o class)
        Remove filter = new Remove();
        filter.setAttributeIndices("" + (dataset.classIndex() + 1));
        filter.setInputFormat(dataset);
        Instances dataClusterer = Filter.useFilter(dataset, filter);

        // Train clusterer
        SimpleKMeans model = new SimpleKMeans();
        model.setNumClusters(5);
        model.buildClusterer(dataClusterer);
        System.out.println(model);

        // evaluate clusterer
        ClusterEvaluation eval = new ClusterEvaluation();
        eval.setClusterer(model);
        eval.evaluateClusterer(test);

        // print results
        System.out.println(eval.clusterResultsToString());

    }

    public static Instances IdentifyClusterAsClasses(Instances dataset,Instances test) throws Exception {
        String[] options = new String[2];
        options[0] = "-I";  // # we want to ignore the class attribute
        options[1] = "last";

        SimpleKMeans model = new SimpleKMeans();
        model.setNumClusters(5);

        AddCluster instanceClasses = new AddCluster();
        instanceClasses.setOptions(options);
        instanceClasses.setInputFormat(dataset);
        //instanceClasses.ignoredAttributeIndicesTipText("_id");
        //instanceClasses.setIgnoredAttributeIndices("" + (dataset.classIndex()));
        Instances resultClusterer = Filter.useFilter(dataset, instanceClasses);

        model.buildClusterer(resultClusterer);

        // evaluate clusterer
        ClusterEvaluation eval = new ClusterEvaluation();
        eval.setClusterer(model);
        eval.evaluateClusterer(test);

        // print results
        System.out.println(eval.clusterResultsToString());

        return resultClusterer;

    }

    public static void RequestToCSV(Object[] contenido, String fullPath) throws IOException {
        // Creacion del data set csv
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(
                objectMapper.writeValueAsBytes(contenido)
        );

        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = jsonNode.elements().next();
        firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);});

        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.writerFor(JsonNode.class)
                .with(csvSchema)
                .writeValue(new File(fullPath),jsonNode);
    }

    public static ArrayList<String> KDTree(String datasetFullPath, int numInstance, int numNeighbors) throws Exception {

        /**
         * Expects a dataset as first parameter. The last attribute is used as class attribute
         * and the first attribute will be excluded from the distance calculation.
         *
         * @param args          the commandline arguments
         * @throws Exception    if something goes wrong
         */


        ConverterUtils.DataSource source = new ConverterUtils.DataSource(datasetFullPath);
        Instances dataset = source.getDataSet();
        dataset.setClassIndex(dataset.numAttributes() - 1);

        Instance inst = dataset.instance(numInstance);
        //dataset.remove(numInstance);

        // initialize KDTree
        EuclideanDistance distfunc = new EuclideanDistance();
        distfunc.setAttributeIndices("2-last");
        KDTree kdtree = new KDTree();
        kdtree.setDistanceFunction(distfunc);
        kdtree.setInstances(dataset);

        // obtain neighbors for a random instance
        //Random rand = dataset.getRandomNumberGenerator(42);

        Instances neighbors = kdtree.kNearestNeighbours(inst, numNeighbors);

        double[] distances = kdtree.getDistances();
        System.out.println("Neighbors data has " + neighbors.numAttributes() + " attributes.");
        System.out.println("\nInstance:\n" + inst);
        System.out.println("\nNeighbors:");

        ArrayList<String> contenidoIDs = new ArrayList<>();

        //for (int i = 0; i < neighbors.numInstances(); i++) {
        for (int i = 0; i < numNeighbors; i++) {
            System.out.println((i + 1) + ". distance=" + distances[i] + "\n   " + neighbors.instance(i) + "");
            //System.out.println(neighbors.instance(i).attribute(1).value((int) neighbors.instance(i).value(0)));
            contenidoIDs.add( neighbors.instance(i).attribute(1).value((int) neighbors.instance(i).value(0)) );
        }

        //SaveInstanceToArff(dataset,"src/main/resources/datasetWithClassesTest.arff");

        return contenidoIDs;
    }

}
