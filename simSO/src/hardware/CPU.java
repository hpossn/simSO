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
    private int baseTime;                     // refere-se ao tempo base da fatia de tempo
    private int maxCPUTime;                      // tempo de cpu
    private int maxJobs;                      // numero maximo de jobs
    
    public CPU(int baseTime, int maxCPUTime, int maxJobs) {
        this.baseTime = baseTime;
        this.maxCPUTime = maxCPUTime;
        this.maxJobs = maxJobs;
        waitingQueue = new WaitingQueue();
        roundQueue = new RoundQueue(maxJobs);
    }
    
    public boolean addJob(Job job, int time) {
        return false;
    }
    
    public void releaseJob(Job job, int time) {
       
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
