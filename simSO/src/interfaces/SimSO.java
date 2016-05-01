package interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import utilitarios.Configurator;
import utilitarios.Event;
import utilitarios.EventList;
import utilitarios.Job;

/**
 *
 * @author hpossani
 */
public class SimSO {
    
    private List<Job> jobsList;
    private Map<String, Integer> paramtersMap;
    private Configurator configurator;
    private EventList eventList;
    private int currentTime;
    private int finalTime;
    
    public static void main(String[] args) {
        SimSO simSO = new SimSO();
        simSO.startSimulator();
    }

    private void startSimulator() {
        try {
            configurator = new Configurator("simulacao.txt");
        } catch (IOException ex) {
            return;
        }
        
        configurator.parseLines();
        
        System.out.println("------------------------------------------------------");
        System.out.println("Inicializacao do simulador");
        System.out.println("------------------------------------------------------\n");
        
        jobsList = configurator.getJobsList();
        paramtersMap = configurator.getParamtersMap();
        
        printParamtersArrival();
        System.out.println();
        printJobArrival();
        
        setInicialParamters();
        setJobsArrivalInEventList();
        
        eventList = new EventList();
        
        simulationLoop();
        
    }
    
    private void printJobArrival() {
        System.out.println("Serao criados os seguintes jobs:");
        
        jobsList.stream().forEach((each) -> {
            System.out.println("\t" + each.completeJobInfo());
        });
    }

    private void printParamtersArrival() {
        System.out.println("Foram criados os seguintes parametros:");
        
        paramtersMap.keySet().stream().forEach((key) -> {
            System.out.println(String.format("\tTipo: %10s, valor: %7d", key, paramtersMap.get(key)));
        });
    }

    private void setJobsArrivalInEventList() {
        jobsList.stream().forEach((job) -> {
            eventList.addEvent(new Event(Event.EventType.NEW_JOB, job, job.getArrivalTime()));
        });
    }
    
    private void setInicialParamters() {
        currentTime = paramtersMap.get("ini_sim");
        finalTime = paramtersMap.get("max_sim");
    }
    
    private void simulationLoop() {
        while(!eventList.isEmpty() && (finalTime - currentTime >= 0)) {
            Event currentEvent = eventList.getCurrentEvent();
            currentTime = currentEvent.getArrivalTime();
            
            switch(currentEvent.getType()) {
                case ALLOCATE_CPU:
                    allocateCPUEvent(currentEvent);
                    break;
                case ALLOCATE_IO:
                    allocateIOEvent(currentEvent);
                    break;
                case ALLOCATE_MEMORY:
                    allocateMemoryEvent(currentEvent);
                    break;
                case NEW_JOB:
                    newJobEvent(currentEvent);
                    break;
                case RELEASE_CPU:
                    releaseCPUEvent(currentEvent);
                    break;
                case RELEASE_IO:
                    releaseIOEvent(currentEvent);
                    break;
                case SHUTDOWN:
                    shutdownEvent(currentEvent);
                    break;
                case START:
                    startEventEvent(currentEvent);
                    break;
                case SWITCH_JOB:
                    switchJobEvent(currentEvent);
                    break;
                case SWITCH_SEGMENT:
                    switchSegmentEvent(currentEvent);
                    break;
                default:
                    errorEvent();
            }
            
            eventList.removeHeadEvent();s
        }
        
    }

    private void newJobEvent(Event currentEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void allocateCPUEvent(Event currentEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void allocateIOEvent(Event currentEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void allocateMemoryEvent(Event currentEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void releaseCPUEvent(Event currentEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void releaseIOEvent(Event currentEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void shutdownEvent(Event currentEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void startEventEvent(Event currentEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void switchJobEvent(Event currentEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void switchSegmentEvent(Event currentEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void errorEvent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
