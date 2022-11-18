package com.zub.weka_springboot_api;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils;
import weka.core.neighboursearch.LinearNNSearch;
import weka.filters.Filter;

import java.io.File;
import java.io.IOException;

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

    public static void ApplyingMethod(String arffPath) throws Exception {
        File fileArff = new File(arffPath);
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(fileArff.getAbsolutePath());
        Instances instances = source.getDataSet();
        instances.setClassIndex(instances.numAttributes()-1);
    }

    public static void KNN(String testPath,String datasetPath) throws Exception {
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(datasetPath);
        Instances dataset = source.getDataSet();
        dataset.setClassIndex(dataset.numAttributes()-1);
        //System.out.println(dataset.toString());

        source = new ConverterUtils.DataSource(testPath);
        Instances test = source.getDataSet();

        dataset.setClassIndex(dataset.numAttributes()-1);
        test.setClassIndex(test.numAttributes()-1);

        // ----------------------------------------------------------------------------------------------

        // remove class attribute, make class-less
        Instances dataClusterer = null;
        weka.filters.unsupervised.attribute.Remove filter = new weka.filters.unsupervised.attribute.Remove();
        filter.setAttributeIndices("" + (dataset.classIndex() + 1));
        try {
            filter.setInputFormat(dataset);
            dataClusterer = Filter.useFilter(dataset, filter);

            SimpleKMeans model = new SimpleKMeans();
            model.setNumClusters(5);
            model.buildClusterer(dataClusterer);
            System.out.println(model);

            ClusterEvaluation testingCluster = new ClusterEvaluation();
            testingCluster.setClusterer(model);
            testingCluster.evaluateClusterer(test);
            System.out.println(testingCluster.getRevision());
            System.out.println(testingCluster.clusterResultsToString());
        } catch (Exception e1) {
            e1.printStackTrace();

        }





        //System.out.println("\n\n "+testingCluster);
    }

}
