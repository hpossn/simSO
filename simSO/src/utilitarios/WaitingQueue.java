/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import java.util.LinkedList;

/**
 *
 * @author hpossani
 */
public class WaitingQueue {
    private final LinkedList<Tuple<Job, Integer>> linkedList;
    
    public WaitingQueue() {
        linkedList = new LinkedList<>();
    }
    
    public String addJob(Job job, int arrivalTime) {
        int insertionPosition = 0;
        
        for(int i = 0; i < linkedList.size(); i++) {
            Job currentJob = linkedList.get(i).getJob();
            int comparacao = job.compareTo(currentJob);
            
            if(comparacao == -1) {
                insertionPosition = i;
                break;
            }
            
            insertionPosition++;
        }
        
        if(insertionPosition < linkedList.size())
            linkedList.add(insertionPosition, new Tuple(job, arrivalTime));
        else
            linkedList.add(new Tuple(job, arrivalTime));
        
        return "Job inserido- " + job.toString() + ", posicao: " + insertionPosition;
    }
    
    public Job removeJob() {
        Tuple<Job, Integer> t = linkedList.poll();
        return t.getJob();
        
       /* if(t != null)
            return "Job removido- " + t.getJob().toString();
        else
            return "Nenhum job em espera";
        */
    }
    
    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();
        msg.append("Lista de jobs em espera:\n");
        
        linkedList.stream().forEach((Tuple<Job, Integer> t) -> {
            msg.append("\t");
            msg.append(t.getJob().toString());
            msg.append("\n");
        });
        
        return msg.toString();
    }
}
