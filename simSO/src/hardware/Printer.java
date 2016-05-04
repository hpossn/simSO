
package hardware;

import java.util.ArrayList;
import java.util.List;
import utilitarios.Job;

/**
 *
 * @author hpossani
 */
public class Printer extends Device {
    
    List<Job> roundRobin;
    private final int MAX_SIZE = 100;
    
    public Printer(int overheadTime, String name) {
        super(overheadTime, name);
        roundRobin = new ArrayList<>(this.MAX_SIZE);
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
    
    @Override
    public int getQueueSize() {
        return roundRobin.size();
    }
    
    public int getMaxSize() {
        return MAX_SIZE;
    }

    @Override
    public boolean insertJob(Job job) {
        if(job == null)
            return false;
        if(roundRobin.size() < MAX_SIZE) {
            roundRobin.add(job);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Job releaseJob(Job job) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Job executeJob(Job job) {
        if(roundRobin.isEmpty())
            return null;
        
        Job j = null;
        int i;
        for(i = 0; i < roundRobin.size(); i++) {
            if(roundRobin.get(i).getJobName().equals(job.getJobName()))
                j = roundRobin.get(i);
        }
        
        i--;
        
        if (j != null) {
            if (!j.hasFinished()) {
                j.decrementIO();
                roundRobin.remove(i);
                roundRobin.add(j);
            } else {
                roundRobin.remove(i);
            }

        } else {
            return job;
        }
        
        
        return j;    
    }

    @Override
    public String statistcsPerJob() {
        StringBuilder msg = new StringBuilder();
        msg.append("Lista de jobs em Round Robin:\n");
        
        roundRobin.stream().forEach((Job j) -> {
            msg.append("\t");
            msg.append(j.toString());
            msg.append("\n");
        });
        
        return msg.toString();
    }

    @Override
    public boolean isJobHere(Job job) {
        Job j = null;
        for(int i = 0; i < roundRobin.size(); i++) {
            if(roundRobin.get(i).getJobName().equals(job.getJobName()))
                return true;
        }
        
        return false;
    }
    
}
