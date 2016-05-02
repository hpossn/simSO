/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hpossani
 */
public class RoundQueue {
    List<Job> roundRobin;
    private final int MAX_SIZE;
    
    public RoundQueue(int maxSize) {
        this.MAX_SIZE = maxSize;
        roundRobin = new ArrayList<>(MAX_SIZE);
    }
    
    public boolean addJob(Job job) {
        if(job == null)
            return false;
        if(roundRobin.size() < MAX_SIZE) {
            roundRobin.add(job);
            return true;
        } else {
            return false;
        }
    }
    
    public Job nextJob() {
        if(roundRobin.isEmpty())
            return null;
        
        Job j = roundRobin.get(0);
        roundRobin.remove(0);
        roundRobin.add(j);
        
        return j;           
    }
    
    public Job getHead() {
        return roundRobin.get(0);
    }
    
    public String getHeadName() {
        return roundRobin.get(0).getJobName();
    }
    
    public Job removeJob() {
        return roundRobin.remove(0);
    }
    
    public boolean removeSpecificJob(Job job) {
        return roundRobin.remove(job);
    }
    
    public boolean isEmpty() {
        return roundRobin.isEmpty();
    }
    
    public int getSize() {
        return roundRobin.size();
    }
    
    public int getMaxSize() {
        return MAX_SIZE;
    }
    
    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();
        msg.append("Lista de jobs em Round Robin:\n");
        
        roundRobin.stream().forEach((Job j) -> {
            msg.append("\t");
            msg.append(j.toString());
            msg.append("\n");
        });
        
        return msg.toString();
    }
    
}
