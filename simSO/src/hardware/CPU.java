package hardware;

import utilitarios.Job;
import utilitarios.RoundQueue;
import utilitarios.WaitingQueue;

/**
 *
 * @author hpossani
 */
public class CPU {
    
    private final WaitingQueue waitingQueue;  // armazena a fila de espera
    private final RoundQueue roundQueue;      // relativo a implementacao do round robin
    private boolean busy;                     // armazena o status da cpu no momento
    private int baseTimeSlice;                     // refere-se ao tempo base da fatia de tempo
    private int maxCPUTime;                   // tempo de cpu
    private int maxJobs;                      // numero maximo de jobs
    
    public CPU(int baseTimeSlice, int maxCPUTime, int maxJobs) {
        this.baseTimeSlice = baseTimeSlice;
        this.maxCPUTime = maxCPUTime;
        this.maxJobs = maxJobs;
        waitingQueue = new WaitingQueue();
        roundQueue = new RoundQueue(maxJobs);
    }
    
    public boolean addJob(Job job, int time) {
        busy = true;
        
        if(!roundQueue.addJob(job)) {
            waitingQueue.addJob(job, time);
            System.out.println("Job: " + job.getJobName() + " foi colocado na fila de espera");
            return false;
        }
        
        System.out.println("Job: " + job.getJobName() + " foi alocado na CPU");
        return true;

    }
    
    public void releaseJob(Job job, int time) {
       boolean remove = roundQueue.removeSpecificJob(job);
       if(remove)
           System.out.println("Job: " + job.getJobName() + " foi removido da CPU");
       else
           System.out.println("Job: " + job.getJobName() + " nao encontrado na CPU");
       
       if(!waitingQueue.isEmpty()) 
           roundQueue.addJob(waitingQueue.removeJob());
       
       if(roundQueue.isEmpty())
           busy = false;
    }
    
    public Job executeTimeSlice() {
        if(!busy)
            return null;
        
        if(roundQueue.isEmpty())
            return null;
        
        Job job = roundQueue.getHead();
        
        job.decrementProcessingTime(baseTimeSlice);
        
        return roundQueue.nextJob();
    }
    
    public boolean hasNext() {
        return false;
    }
    
    public boolean isBusy() {
        return busy;
    }
    
    @Override
    public String toString() {
        return "";
    }
}
