/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

/**
 *
 * @author hpossani
 */
public class Event implements Comparable<Event> {
    
    public enum EventType {
        START, NEW_JOB, ALLOCATE_MEMORY, ALLOCATE_CPU,
        RELEASE_CPU, ALLOCATE_IO, RELEASE_IO, SWITCH_JOB, SWITCH_SEGMENT, MEMORY_READY, SHUTDOWN
    }
    
    private final EventType type;
    private final Job job;
    private final int arrivalTime;
    
    public Event(EventType type, Job job, int arrivalTime) {
        this.type = type;
        this.job = job;
        this.arrivalTime = arrivalTime;
    }

    public EventType getType() {
        return type;
    }

    public Job getJob() {
        return job;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
    
    @Override
    public String toString() {
        return "Tipo: " + type.toString() + " Job: " + job.getJobName() + " Chegada: " + arrivalTime + "\n";
    }
    
    @Override
    public int compareTo(Event t) {
        if(this.arrivalTime < t.getArrivalTime())
            return -1;
        if(this.arrivalTime == t.getArrivalTime())
            return 0;
        else
            return 1;
    }
    
}
