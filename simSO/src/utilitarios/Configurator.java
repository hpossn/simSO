/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hpossani
 */
public class Configurator {
    
    private final String fileName;
    private final Map<String, Integer> paramters;
    private final List<Job> jobs;
    private final List<String> inputLines;
    
    public Configurator(String fileName) throws IOException {
        this.fileName = fileName;
        paramters = new HashMap<>();
        jobs = new ArrayList<>();
        inputLines = new ArrayList<>();
        boolean sucess = readInputFile();
        
        if(!sucess)
            throw new IOException();
    }

    private boolean readInputFile() {
        BufferedReader bufferedReader;

        try {
            FileReader fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                inputLines.add(line);
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado.");
            return false;
        } catch (IOException e) {
            System.out.println("Problema inesperado com o arquivo de entrada.");
            return false;
        }
        
        return true;
    }
    
    public void parseLines() {
        inputLines.stream().map((eachLine) -> Arrays.asList(eachLine.split(":", 2))).forEach((line) -> {
            if(line.get(0).equals("job"))
                createJob(line.get(1));
            else
                createParamter(line);
        });
    }

    private void createJob(String line) {
        List<String> jobParam = Arrays.asList(line.split(";"));
        //System.out.println("JOB! Conteudo da string linha \"" + line + "\" e da lista: " + jobParam.toString());
        String jobName = jobParam.get(0);
        int arrivalTime = Integer.parseInt(jobParam.get(1));
        int priority = Integer.parseInt(jobParam.get(2));
        int cpuTime = Integer.parseInt(jobParam.get(3));
        int numIO = Integer.parseInt(jobParam.get(4));
        int sizeIO = Integer.parseInt(jobParam.get(5));
        int currentSegment = Integer.parseInt(jobParam.get(6));
        
        int[] blockSizes = getBlockSize(jobParam.get(7));
        Map<Integer, List<Integer>> dependencies = getSegmentDependencies(jobParam.get(8));
        
        Job job = new Job(arrivalTime, cpuTime, currentSegment, priority, jobName, numIO, sizeIO, blockSizes, dependencies);
        
        jobs.add(job);
    }   
    
    private int[] getBlockSize(String sizes) {
        List<String> blockSizes = Arrays.asList(sizes.split(" "));
        int[] sizeArray = new int[blockSizes.size()];
        
        for(int i = 0; i < sizeArray.length; i++) {
            sizeArray[i] = Integer.parseInt(blockSizes.get(i));
        }
        
        return sizeArray;
    }
    
    private Map<Integer, List<Integer>> getSegmentDependencies(String allDependencies) {
        Map<Integer, List<Integer>> mapOfDependencies = new HashMap<>();
        
        for(String each : Arrays.asList(allDependencies.split(" "))) {
            List<String> eachDependency = Arrays.asList(each.split(":"));
            
            int key = Integer.parseInt(eachDependency.get(0));
            
            List<Integer> valueOfDependencies = new ArrayList<>();
            
            for(String st : Arrays.asList(eachDependency.get(1).split(","))) {
                valueOfDependencies.add(Integer.parseInt(st));
            }
            
            mapOfDependencies.put(key, valueOfDependencies);
            
            //System.out.println("Dependencia criada key: " + key + " dependencias: " + valueOfDependencies.toString());
        }
        
        return mapOfDependencies;
    }
    
    public List<Job> getJobsList() {
        return jobs;
    }
    
    public Map<String, Integer> getParamtersMap() {
        return paramters;
    }

    private void createParamter(List<String> line) {
        //System.out.println("Novo parametro");
        String type = line.get(0);
        int value = Integer.parseInt(line.get(1));
       
        paramters.put(type, value);
        
    }
}
