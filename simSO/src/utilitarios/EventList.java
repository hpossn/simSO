
package utilitarios;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hpossani
 */
public class EventList {
    private final List<Event> eventList;
    
    public EventList() {
        eventList = new ArrayList<>();
    }
    
    public void addEvent(Event event) {
        int posicao = 0;
        int i;
        
        if(event.getType() == Event.EventType.SHUTDOWN) {
            if(eventList.get(eventList.size() - 1).getType() == Event.EventType.SHUTDOWN) {
                return;
            }
        }
        
        for(i = 0; i < eventList.size(); i++) {
            if(event.compareTo(eventList.get(i)) < 0) {
                posicao = i;
                break;
            }
        }
        
        if(posicao == 0 && i > 0)
            eventList.add(event);
        else
            eventList.add(posicao, event);
    }
    
    public Event removeHeadEvent() {
        return eventList.remove(0);
    }
    
    public Event getCurrentEvent() {
        return eventList.get(0);
    }
    
    public boolean isEmpty() {
        return eventList.isEmpty();
    }
    
    public int getLastTime() {
        return eventList.get(eventList.size() - 1).getArrivalTime();
    }
    
    public void removeAll() {
        eventList.clear();
    }
    
    @Override
    public String toString() {
        return eventList.toString();
    }
    
}
