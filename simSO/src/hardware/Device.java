/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hardware;

import utilitarios.Job;
import utilitarios.WaitingQueue;

/**
 *
 * @author hpossani
 */
public abstract class Device {
    protected WaitingQueue waitingQueue;
    protected boolean isBusy;
    protected int overheadTime;
    protected String name;

    public Device(int overheadTime, String name) {
        waitingQueue = new WaitingQueue();
        this.overheadTime = overheadTime;
        this.isBusy = false;
        this.name = name;
    }

    public boolean isIsBusy() {
        return isBusy;
    }
    
    public abstract boolean insertJob(Job job);
    public abstract Job releaseJob(Job job);
    public abstract Job executeJob(Job job);
    public abstract String statistcsPerJob();
    public abstract int getQueueSize();
    public abstract boolean isJobHere(Job job);
    public String getName() {return name;}
}
