package interfaces;

import hardware.CPU;
import hardware.Memory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utilitarios.Event;
import utilitarios.Job;
import utilitarios.RoundQueue;
import utilitarios.WaitingQueue;

/**
 *
 * @author hpossani
 */
public class testes {

    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        //testarWaitingQueue();
        //testarRoundQueue();
        //testarMemoria();
        //testarEvento();
        //testarCPU();
    }*/
    
    public static void testarCPU() {
        CPU cpu = new CPU(10, 2);
        Map<Integer, List<Integer>> depen1;
        depen1 = new HashMap<>();
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        List<Integer> list11 = new ArrayList<>();
        list11.add(0);
        list11.add(3);
        depen1.put(0, list1);
        depen1.put(1, list11);
        int[] v1 = {1, 2, 3, 4};
        
        Job job1 = new Job(5, 0, 1, 1, "Job1", 0, 0, v1, depen1);
        
        cpu.addJob(job1, 5);
        
        Map<Integer, List<Integer>> depen2;
        depen2 = new HashMap<>();
        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(3);
        list2.add(1);
        List<Integer> list21 = new ArrayList<>();
        list21.add(2);
        list21.add(3);
        depen2.put(0, list2);
        depen2.put(1, list21);
        int[] v2 = {4, 1, 5, 4};
        
        Job job2 = new Job(7, 0, 0, 0, "Job2", 0, 0, v2, depen2);
        
        cpu.addJob(job2, 7);
        
        Job job3 = new Job(7, 0, 0, 0, "Job3", 0, 0, v2, depen2);
        
        cpu.addJob(job3, 7);
        
        cpu.executeTimeSlice();
        cpu.executeTimeSlice();
        cpu.executeTimeSlice();
        cpu.executeTimeSlice();
        cpu.executeTimeSlice();
        cpu.executeTimeSlice();
        cpu.executeTimeSlice();
        cpu.executeTimeSlice();
        
        
        
        
    }
    
    public static void testarEvento() {
        Map<Integer, List<Integer>> depen1;
        depen1 = new HashMap<>();
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        List<Integer> list11 = new ArrayList<>();
        list11.add(0);
        list11.add(3);
        depen1.put(0, list1);
        depen1.put(1, list11);
        int[] v1 = {1, 2, 3, 4};
        
        Job job1 = new Job(5, 0, 1, 1, "Job1", 0, 0, v1, depen1);
        
        Event event = new Event(Event.EventType.START, job1, 0);
        
        System.out.println(event.toString());
    }
    
    public static void testarMemoria() {
        Map<Integer, List<Integer>> depen1;
        depen1 = new HashMap<>();
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        List<Integer> list11 = new ArrayList<>();
        list11.add(0);
        list11.add(3);
        depen1.put(0, list1);
        depen1.put(1, list11);
        int[] v1 = {1, 2, 3, 4};
        
        Map<Integer, List<Integer>> depen2;
        depen2 = new HashMap<>();
        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(3);
        list2.add(1);
        List<Integer> list21 = new ArrayList<>();
        list21.add(2);
        list21.add(3);
        depen2.put(0, list2);
        depen2.put(1, list21);
        int[] v2 = {4, 1, 5, 4};
        
        Map<Integer, List<Integer>> depen3;
        depen3 = new HashMap<>();
        List<Integer> list3 = new ArrayList<>();
        list3.add(1);
        list3.add(2);
        List<Integer> list31 = new ArrayList<>();
        list31.add(0);
        depen3.put(0, list3);
        depen3.put(1, list31);
        int[] v3 = {4, 4, 5, 4};
        
        
        Job job1 = new Job(5, 0, 1, 1, "Job1", 0, 0, v1, depen1);
        Job job2 = new Job(7, 0, 0, 0, "Job2", 0, 0, v2, depen2);
        Job job3 = new Job(7, 0, 0, 1, "Job3", 0, 0, v3, depen3);
        Job job4 = new Job(8, 0, 0, 1, "Job1", 0, 0, v1, depen1);
        Job job5 = new Job(10, 0, 0, 0, "Job2", 0, 0, v2, depen2);
        Job job6 = new Job(4, 0, 0, 3, "Job3", 0, 0, v3, depen3);
        Job job7 = new Job(3, 0, 0, 4, "Job1", 0, 0, v1, depen1);
        Job job8 = new Job(12, 0, 0, 1, "Job2", 0, 0, v2, depen2);
        
        Memory memoria = new Memory(30, 30, 2);
        
        System.out.println("Teste de memoria");
        System.out.println("");
        
        memoria.addJob(job1, 0);
        System.out.println(memoria.toString());
        /*memoria.addJob(job2, 0);
        System.out.println(memoria.toString());*/
        memoria.addJob(job3, 0);
        System.out.println(memoria.toString());
        memoria.addJob(job4, 0);
        System.out.println(memoria.toString());
        
        System.out.println("Segmento atual para job1: " + job1.getCurrentSegment());
        System.out.println("Proximo segmento para job1: " + job1.getNextSegment());
        
        System.out.println("Segmento atual para job1: " + job1.getCurrentSegment());
        System.out.println("Proximo segmento para job1: " + job1.getNextSegment());
        
        System.out.println("Segmento atual para job1: " + job1.getCurrentSegment());
        System.out.println("Proximo segmento para job1: " + job1.getNextSegment());
        
    }
    
    public static void testarWaitingQueue() {
        Map<Integer, List<Integer>> depen1;
        depen1 = new HashMap<>();
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        List<Integer> list11 = new ArrayList<>();
        list11.add(0);
        list11.add(3);
        depen1.put(0, list1);
        depen1.put(1, list11);
        int[] v1 = {1, 2, 3, 4};
        
        Map<Integer, List<Integer>> depen2;
        depen2 = new HashMap<>();
        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(4);
        list2.add(1);
        List<Integer> list21 = new ArrayList<>();
        list21.add(2);
        list21.add(3);
        depen2.put(0, list2);
        depen2.put(1, list21);
        int[] v2 = {4, 1, 5, 4};
        
        Map<Integer, List<Integer>> depen3;
        depen3 = new HashMap<>();
        List<Integer> list3 = new ArrayList<>();
        list3.add(1);
        list3.add(2);
        List<Integer> list31 = new ArrayList<>();
        list31.add(0);
        depen3.put(0, list3);
        depen3.put(1, list31);
        int[] v3 = {1, 1, 5, 4};
        
        
        Job job1 = new Job(5, 0, 1, 1, "Job1", 0, 0, v1, depen1);
        Job job2 = new Job(7, 0, 0, 0, "Job2", 0, 0, v2, depen2);
        Job job3 = new Job(7, 0, 0, 1, "Job3", 0, 0, v3, depen3);
        Job job4 = new Job(8, 0, 0, 1, "Job1", 0, 0, v1, depen1);
        Job job5 = new Job(10, 0, 0, 0, "Job2", 0, 0, v2, depen2);
        Job job6 = new Job(4, 0, 0, 3, "Job3", 0, 0, v3, depen3);
        Job job7 = new Job(3, 0, 0, 4, "Job1", 0, 0, v1, depen1);
        Job job8 = new Job(12, 0, 0, 1, "Job2", 0, 0, v2, depen2);
        
        WaitingQueue waitingQueue = new WaitingQueue();
        
        System.out.println(waitingQueue.addJob(job1, 5));
        System.out.println(waitingQueue.addJob(job2, 7));
        System.out.println(waitingQueue.addJob(job3, 7));
        System.out.println(waitingQueue.addJob(job4, 8));
        System.out.println(waitingQueue.addJob(job5, 10));
        System.out.println(waitingQueue.addJob(job6, 4));
        System.out.println(waitingQueue.addJob(job7, 3));
        System.out.println(waitingQueue.addJob(job8, 12));
        
        System.out.println();
        
        System.out.println(waitingQueue.toString());
    }
    
    public static void testarRoundQueue() {
        Map<Integer, List<Integer>> depen1;
        depen1 = new HashMap<>();
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        List<Integer> list11 = new ArrayList<>();
        list11.add(0);
        list11.add(3);
        depen1.put(0, list1);
        depen1.put(1, list11);
        int[] v1 = {1, 2, 3, 4};
        
        Map<Integer, List<Integer>> depen2;
        depen2 = new HashMap<>();
        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(4);
        list2.add(1);
        List<Integer> list21 = new ArrayList<>();
        list21.add(2);
        list21.add(3);
        depen2.put(0, list2);
        depen2.put(1, list21);
        int[] v2 = {4, 1, 5, 4};
        
        Map<Integer, List<Integer>> depen3;
        depen3 = new HashMap<>();
        List<Integer> list3 = new ArrayList<>();
        list3.add(1);
        list3.add(2);
        List<Integer> list31 = new ArrayList<>();
        list31.add(0);
        depen3.put(0, list3);
        depen3.put(1, list31);
        int[] v3 = {1, 1, 5, 4};
        
        
        Job job1 = new Job(5, 0, 1, 1, "Job1", 0, 0, v1, depen1);
        Job job2 = new Job(7, 0, 0, 0, "Job2", 0, 0, v2, depen2);
        Job job3 = new Job(7, 0, 0, 1, "Job3", 0, 0, v3, depen3);
        Job job4 = new Job(8, 0, 0, 1, "Job1", 0, 0, v1, depen1);
        Job job5 = new Job(10, 0, 0, 0, "Job2", 0, 0, v2, depen2);
        Job job6 = new Job(4, 0, 0, 3, "Job3", 0, 0, v3, depen3);
        Job job7 = new Job(3, 0, 0, 4, "Job1", 0, 0, v1, depen1);
        Job job8 = new Job(12, 0, 0, 1, "Job2", 0, 0, v2, depen2);
        
        
        RoundQueue roundQueue = new RoundQueue(5);
        
        if(roundQueue.addJob(job1))
            System.out.println("Adicionado job1");
        else
            System.out.println("Nao ha espaco para job1");
        
        if(roundQueue.addJob(job2))
            System.out.println("Adicionado job2");
        else
            System.out.println("Nao ha espaco para job2");
        
        if(roundQueue.addJob(job3))
            System.out.println("Adicionado job3");
        else
            System.out.println("Nao ha espaco para job3");
        
        if(roundQueue.addJob(job4))
            System.out.println("Adicionado job4");
        else
            System.out.println("Nao ha espaco para job4");
        
        if(roundQueue.addJob(job5))
            System.out.println("Adicionado job5");
        else
            System.out.println("Nao ha espaco para job5");
        
        if(roundQueue.addJob(job6))
            System.out.println("Adicionado job6");
        else
            System.out.println("Nao ha espaco para job6");
        
        if(roundQueue.addJob(job7))
            System.out.println("Adicionado job7");
        else
            System.out.println("Nao ha espaco para job7");
        
        if(roundQueue.addJob(job8))
            System.out.println("Adicionado job8");
        else
            System.out.println("Nao ha espaco para job8");
    }
}
