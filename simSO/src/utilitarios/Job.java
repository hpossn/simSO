package utilitarios;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author hpossani
 */
public class Job implements Comparable<Job> {
    private final int arrivalTime;            // tempo de chegada
    private int processingTime;         // tempo de cpu
    private final int priority;               // prioridade
    private int currentSegment;               // segmento atual
    private int totalNeededSpace;             // espaco total utilizado
    private int numChamadasEntradaSaida;       // contabiliza o numero de chamadas de entrada e saida
    private final int[] segments;             // armazena os segmentos necessarios (cada posicao do array e o tamanho do segmento)
    private final String jobName;             // nome do Job
    private final Map<Integer, List<Integer>> segmentDependencies;    //Mapa com dependencia de segmentos
    

    public Job(int arrivalTime, int processingTime, int currentSegment, int priority,
            String jobName, int[] segments, Map<Integer, List<Integer>> segmentDependencies) {
        
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
        this.priority = priority;
        this.jobName = jobName;
        this.segments = segments;
        this.segmentDependencies = segmentDependencies;
        this.currentSegment = currentSegment;
        
        calculateTotalNeededSpace();
    }
    
    public int getProcessingTime() {
        return processingTime;
    }
    
    public void decrementProcessingTime(int step) {
        this.processingTime -= step;
    }
    
    public int getNextSegment() {
        Random rand = new Random();
        
        List<Integer> listDep = segmentDependencies.get(currentSegment);
        int seed = 0; 
        
        if(listDep != null)
            seed = listDep.size();
        
        if(seed > 0)
            currentSegment = segmentDependencies.get(currentSegment).get(rand.nextInt(seed));
        
        return currentSegment;
    }
    
    private void calculateTotalNeededSpace() {
        for(int i : segments)
            totalNeededSpace += i;
    }
    
    private int getFreeSpaceNeededForSegmentTree(int numSeg) {
        int total = segments[numSeg];
        
        for(int i : segmentDependencies.get(numSeg)) {
            total += segments[i];
        }
        
        return total;
    }
    
    public int getFreeSpaceNeeded() {
        return getFreeSpaceNeededForSegmentTree(currentSegment);
    }
    
    public List<Integer> getCurrentSegmentDependencies() {
        return segmentDependencies.get(currentSegment);
    }
    
    public int getCurrentSegmentSize() {
        return segments[currentSegment];
    }
    
    public int getSegmentSize(int segment) {
        return segments[segment];
    }
    
    public int getCurrentSegment() {
        return currentSegment;
    }
    
    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getPriority() {
        return priority;
    }
    
    public String getJobName() {
        return jobName;
    }
    
    public void setCurrentSegment(int currentSegment) {
        this.currentSegment = currentSegment;
    }
    
    @Override
    public int compareTo(Job t) {        
        if(this.priority > t.getPriority())
            return 1;
        
        if(this.priority == t.getPriority()) {
            if(this.arrivalTime > t.getArrivalTime())
                return 1;
            else if(this.arrivalTime == t.getArrivalTime())
                return 0;
            else
                return -1;
        } else {
            return -1;
        }
    }
    
    @Override
    public String toString() {
        return "Job: " + jobName + ", Tempo de chegada: " + arrivalTime;
    }
    
}
