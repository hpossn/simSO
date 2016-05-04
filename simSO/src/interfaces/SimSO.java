package interfaces;

import hardware.CPU;
import hardware.Device;
import hardware.Memory;
import hardware.Printer;
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
    private int memoryMaxSize;
    private int realocTime;
    private int baseTimeSlice;
    private int maxJobs;
    private int overhead;
    private int currentCPUAlocation;
    
    private Memory memory;
    private CPU cpu;
    private Device printer1;
    private Device printer2;
    
    private Job finalJob;
    
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
        
        System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.println("Inicializacao do simulador");
        System.out.println("------------------------------------------------------------------------------------------------------------\n");
        
        jobsList = configurator.getJobsList();
        paramtersMap = configurator.getParamtersMap();
        eventList = new EventList();
        
        printParamtersArrival();
        System.out.println();
        printJobArrival();
        
        setInicialParamters();
        setJobsArrivalInEventList();
        
        memory = new Memory(memoryMaxSize, realocTime);
        cpu = new CPU(baseTimeSlice, maxJobs, overhead);
        
        System.out.println("------------------------------------------------------------------------------------------------------------\n");
        
        simulationLoop();
        
    }
    
    private void printJobArrival() {
        System.out.println("Serao criados os seguintes jobs:");
        
        jobsList.stream().forEach((each) -> {
            System.out.println("\t" + each.completeJobInfo());
        });
    }

    private void printParamtersArrival() {
        System.out.println("Foram configurados os seguintes parametros:");
        
        paramtersMap.keySet().stream().forEach((key) -> {
            System.out.println(String.format("\tTipo: %12s, valor: %7d", key, paramtersMap.get(key)));
        });
    }

    private void setJobsArrivalInEventList() {
        jobsList.stream().forEach((job) -> {
            eventList.addEvent(new Event(Event.EventType.NEW_JOB, job, job.getArrivalTime()));
            
            /*if(job.getNumIO() > 0) {
                eventList.addEvent(new Event(Event.EventType.ALLOCATE_IO, job, job.getArrivalTime() + overhead));
            } */
        });
        
        eventList.addEvent(new Event(Event.EventType.SHUTDOWN, finalJob, finalTime));
        
        
    }
    
    private void setInicialParamters() {
        currentTime = paramtersMap.get("ini_sim");
        finalTime = paramtersMap.get("max_sim");
        memoryMaxSize = paramtersMap.get("max_memory");
        realocTime = paramtersMap.get("realoc_time");
        baseTimeSlice = paramtersMap.get("time_slice");
        maxJobs = paramtersMap.get("max_jobs");
        overhead = paramtersMap.get("over_head");
        
        printer1 = new Printer(overhead, "Impressora 1");
        printer2 = new Printer(overhead, "Impressora 2");
        
        int[] i = {1};
        finalJob = new Job(finalTime, 0, 0, 0, "final", 0, 0, i, null);
    }
    
    private void simulationLoop() {
        Event currentEvent;
        
        while(!eventList.isEmpty() && (finalTime - currentTime >= 0)) {
            currentEvent = eventList.getCurrentEvent();
            
            //System.out.println("\n\n" + eventList.toString() + "\n\n");
            
            updateTime(currentEvent.getArrivalTime());
            
            Event.EventType eventType = currentEvent.getType();
            
            switch(eventType) {
                case ALLOCATE_CPU:
                    allocateCPUEvent(currentEvent);
                    break;
                case ALLOCATE_IO:
                    allocateIOEvent(currentEvent);
                    break;
                case ALLOCATE_MEMORY:
                    allocateMemoryEvent(currentEvent);
                    break;
                case MEMORY_READY:
                    memoryReadyEvent(currentEvent);
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
            
            if(!eventList.isEmpty())
                eventList.removeHeadEvent();
        }
        
    }

    private void newJobEvent(Event currentEvent) {
        System.out.println(String.format("(%6d) Evento: Chegada de %s", currentTime, currentEvent.getJob().getJobName()));
        eventList.addEvent(new Event(Event.EventType.ALLOCATE_MEMORY, currentEvent.getJob(), currentTime + overhead));
    }

    private void allocateCPUEvent(Event currentEvent) {
        Job job = currentEvent.getJob();
        
        cpu.addJob(job, currentTime);
        
        System.out.println(String.format("(%6d) Evento: Alocacao de CPU para  %s. %s",
                currentTime, job.getJobName(), cpu.getMsg()));
        
        int t = eventList.getFurthestSwitchJob();

        if (t == -1) {
            t = currentTime + baseTimeSlice + overhead;
        } else {
            t += baseTimeSlice + overhead;
        }
        
        if(job.getNumIO() > 0) {
                eventList.addEvent(new Event(Event.EventType.ALLOCATE_IO, job, t + 1));
        }
        
        eventList.addEvent(new Event(Event.EventType.SWITCH_JOB, job, t));
    }

    private void allocateIOEvent(Event currentEvent) {
        
        Device  printer;
        if(printer1.getQueueSize() <= printer2.getQueueSize()) {
            printer = printer1;
        } else {
            printer = printer2;
        }
        
        System.out.println(String.format("(%6d) Evento: Alocacao de dispositivo %s para %s", currentTime, 
                printer.getName(), currentEvent.getJob().getJobName()));
        
        printer.insertJob(currentEvent.getJob());
    }

    private void allocateMemoryEvent(Event currentEvent) {
        Job job = currentEvent.getJob();
        
        
        if(!memory.addJob(job, currentTime)) 
            System.out.println(String.format("(%6d) Evento: Espaco indisponivel para %s, foi para o disco.",
                    currentTime, job.getJobName()));
        else {            
            System.out.println(String.format("(%6d) Evento: Alocacao de Memoria para %s no segmento atual %d em andamento", currentTime, 
                job.getJobName(), job.getCurrentSegment()));
            eventList.addEvent(new Event(Event.EventType.MEMORY_READY, job, memory.getRunningTime() + currentTime));
        }
    }

    private void releaseCPUEvent(Event currentEvent) {
        Job job1 = currentEvent.getJob();
        
        Job job2 = cpu.releaseJob(job1, currentTime);
        
        memory.removeJob(job1);
        
        Job job3 = memory.fromWaitToMemory();
        
        int t = eventList.getFurthestSwitchJob();

        if (t == -1) {
            t = currentTime + baseTimeSlice + overhead;
        } else {
            t += baseTimeSlice + overhead;
        }
        
        
        if(!cpu.getMsg().contains("nao encontrado na CPU"))        
            System.out.println(String.format("(%6d) Evento: Saida da CPU. %s",
                currentTime, cpu.getMsg()));
        
        if(job3 != null) {
            eventList.addEvent(new Event(Event.EventType.MEMORY_READY, job3, currentTime + memory.getRunningTime() + 1));
            System.out.println(String.format("(%6d) Evento: Espaco liberado na memoria para %s.", currentTime, job3.getJobName()));
        }
        
        if(job2 != null) {
            System.out.println(String.format("(%6d) Evento: CPU Disponivel. %s foi para a memoria e esta na CPU.",
                currentTime, job2.getJobName()));
            
            
            eventList.addEvent(new Event(Event.EventType.SWITCH_JOB, job2, t));
            
            //System.out.println(eventList.toString());
        }
    }

    private void releaseIOEvent(Event currentEvent) {
        Device printer;
        printer = printer1.isJobHere(currentEvent.getJob()) ? printer1 : printer2;
        
        System.out.println(String.format("(%6d) Evento: %s liberou dispositivo %s",
                currentTime, currentEvent.getJob().getJobName(), printer.getName()));
    }

    private void shutdownEvent(Event currentEvent) {
        eventList.removeAll();
        System.out.println(String.format("(%6d) Evento: Final da simulacao",
                currentTime));
    }
    
    private void startEventEvent(Event currentEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void switchJobEvent(Event currentEvent) {        
        Job job = cpu.executeTimeSlice();
        
        if(job == null) {
            System.out.println(String.format("(%6d) Evento: Execucao. %s",
                currentTime, cpu.getMsg()));
        } else {
            if(!cpu.getMsg().equals("finished")) {
                System.out.println(String.format("(%6d) Evento: Execucao. %s",
                currentTime, cpu.getMsg()));
                
                if(!job.hasFinished()) {
                    eventList.addEvent(new Event(Event.EventType.SWITCH_SEGMENT, job, currentTime + overhead));
                    
                    if(!job.hasFinishedIO()) {
                        Device printer;
                        
                        if(printer1.insertJob(job)) {
                            printer = printer1;
                        } else {
                            printer = printer2;
                        }
                        
                        Job printerJob = printer.executeJob(job);
                        
                        if(printerJob != null) {
                            if(printerJob.hasFinishedIO()) {
                                eventList.addEvent(new Event(Event.EventType.RELEASE_IO, job, currentTime + overhead + 1));
                            }
                        }
                    }
                }
            }
            
            if(currentEvent.getJob().hasFinished()) {
                eventList.addEvent(new Event(Event.EventType.RELEASE_CPU, currentEvent.getJob(), currentTime + overhead));
            }
        }
    }

    private void switchSegmentEvent(Event currentEvent) {
        Job job = currentEvent.getJob();
        
        int old = job.getCurrentSegment();
        int segment = job.getNextSegment();
        
        int t = eventList.getFurthestSwitchJob();
        
        System.out.println(String.format("(%6d) Evento: Troca de segmento em %s %d -> %d",
                currentTime, currentEvent.getJob().getJobName(), old, segment));

        if (t == -1) {
            t = currentTime + baseTimeSlice + overhead;
        } else {
            t += baseTimeSlice + overhead;
        }

        eventList.addEvent(new Event(Event.EventType.SWITCH_JOB, job, t));
        
    }

    private void errorEvent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void memoryReadyEvent(Event currentEvent) {
        System.out.println(String.format("(%6d) Evento: Alocacao de memoria concluida para %s: %s",
                currentTime, currentEvent.getJob().getJobName(),"Segmento e suas dependencias alocados com sucesso"));
        
        eventList.addEvent(new Event(Event.EventType.ALLOCATE_CPU, currentEvent.getJob(), currentTime + overhead));
    }

    private void updateTime(int arrivalTime) {
        if(currentTime < arrivalTime) {
            currentTime = arrivalTime;
        }
    }
}
