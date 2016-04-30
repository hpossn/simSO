package hardware;

import java.util.ArrayList;
import java.util.List;
import utilitarios.Job;
import utilitarios.MemoryBlock;
import utilitarios.WaitingQueue;

/**
 *
 * @author hpossani
 */
public class Memory {
    
    private final int MAX_SIZE;         // Tamanho maximo da memoria
    private int freeSize;               // Tamanho livre
    
    private int realocTime;             // Configura o tempo de armazenamento
    private int runningTime = 0;        // Configura o tempo de operacao
    
    private WaitingQueue waitingQueue;
    private List<MemoryBlock> blockList;

    public Memory(int MAX_SIZE, int freeSize, int realocTime) {
        this.MAX_SIZE = MAX_SIZE;
        this.freeSize = freeSize;
        this.realocTime = realocTime;
        waitingQueue = new WaitingQueue();
        blockList = new ArrayList<>();
    }
    
    public boolean addJob(Job job, int arrivalTime) {
        if(hasSegment(job.getJobName(), job.getCurrentSegment())) {
            boolean contem = true;
            for(int dep : job.getCurrentSegmentDependencies()) {
                if(!hasSegment(job.getJobName(), dep)) {
                    contem = false;
                    break;
                }
            }
            
            if(contem) {
                System.out.println("O segmento e suas dependencias ja foi alocado anteriormente");
                return true;
            }
        }
        
        if(freeSize < job.getFreeSpaceNeeded()) {
            waitingQueue.addJob(job, arrivalTime);
            System.out.println("Espaco indisponivel. Fila de espera");
            return false;
        }
        
        if(!hasSegment(job.getJobName(), job.getCurrentSegment())) {
            blockList.add(new MemoryBlock(job.getJobName(), job.getCurrentSegment(), job.getCurrentSegmentSize()));
            freeSize -= job.getCurrentSegmentSize();
            runningTime += realocTime;
        }
        
        List<Integer> dependencies = job.getCurrentSegmentDependencies();
        
        for(int segment : dependencies) {
            if(!hasSegment(job.getJobName(), segment)) {
                blockList.add(new MemoryBlock(job.getJobName(), segment, job.getSegmentSize(segment)));
                freeSize -= job.getSegmentSize(segment);
                runningTime += realocTime;
            }   
        }
            System.out.println("Segmento e suas dependencias alocados com sucesso");
        
        return true;
           
    }
    
    public void removeJob(Job job) {
        for(int i = 0; i < blockList.size(); i++) {
            if(blockList.get(i).getId().equals(job.getJobName())) {
                freeSize += blockList.get(i).getSizeSeg();
                blockList.remove(i);
            }
        }
    }
    
    private boolean hasSegment(String id, int segNum) {
        for(int i = 0; i < blockList.size(); i++) {
            if(blockList.get(i).getId().equals(id)) {
                if(segNum == blockList.get(i).getNumSeg()) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();
        msg.append("Memoria Principal\n");
        msg.append("\nLista de segmentos:\n");
        
        for(MemoryBlock mb : blockList) {
            msg.append(String.format("\tID: %s, Numero: %d, Tamanho: %d\n", mb.getId(), mb.getNumSeg(), mb.getSizeSeg()));
        }
        
        msg.append("\n");
        msg.append(waitingQueue.toString());
        
        msg.append("\nEspaco Livre: " + freeSize + "\n");
        msg.append("\nTempo de operacao: " + runningTime + "\n");
        
        return msg.toString();
    }
}
