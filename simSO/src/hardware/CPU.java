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
    private final int baseTimeSlice;                // refere-se ao tempo base da fatia de tempo
    private final int maxJobs;                      // numero maximo de jobs
    private final int overhead;
    private String msg;
    
    public CPU(int baseTimeSlice, int maxJobs, int overhead) {
        this.baseTimeSlice = baseTimeSlice;
        this.maxJobs = maxJobs;
        waitingQueue = new WaitingQueue();
        this.overhead = overhead;
        roundQueue = new RoundQueue(this.maxJobs);
    }
    
    public boolean addJob(Job job, int time) {
        busy = true;
        
        if(!roundQueue.addJob(job)) {
            waitingQueue.addJob(job, time);
            msg = job.getJobName() + " foi colocado na fila de espera";
            return false;
        }
        
        msg = job.getJobName() + " foi alocado na CPU";
        return true;

    }
    
    public Job releaseJob(Job job, int time) {
       boolean remove = roundQueue.removeSpecificJob(job);
       Job job2 = null;
       if(remove) {
           msg = job.getJobName() + " foi removido da CPU";
        
           if (!waitingQueue.isEmpty()) {
               job2 = waitingQueue.removeJob();
               roundQueue.addJob(job2);
           }
       
       }
       else
           msg = "nao encontrado na CPU";
       
       
       
       if(roundQueue.isEmpty() && waitingQueue.isEmpty())
           busy = false;
       
       return job2;
    }
    
    public Job executeTimeSlice() {
        /*if(!busy) {
            msg = "Nada a ser executado";
            return null;
        }*/
        
        if(roundQueue.isEmpty()) {
            msg = "Nenhum JOB alocado";
            return null;
        } else {
            Job job = roundQueue.getHead();
            
            if(job.hasFinished())
                msg = "finished";
            else {
                job.decrementProcessingTime(baseTimeSlice);

                msg = roundQueue.getHeadName() + " em execucao por um timeslice";
            }
            return roundQueue.nextJob();
        }
    }
    
    public boolean hasNext() {
        return !(roundQueue.getSize() <= 1);
    }
    
    public boolean isBusy() {
        return busy;
    }
    
    @Override
    public String toString() {
        return "";
    }
    
    public String getMsg() {
        return msg;
    }
}
