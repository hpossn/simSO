package utilitarios;

/**
 *
 * @author hpossani
 * @param <Integer>
 */
public class Tuple <Job, Integer> {
    
    private final Job job;
    private final int time;

    public Tuple(Job job, int time) {
        this.job = job;
        this.time = time;
    }

    public Job getJob() {
        return job;
    }

    public int getTime() {
        return time;
    }
    
    
}
