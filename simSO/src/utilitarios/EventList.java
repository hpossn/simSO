
package utilitarios;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hpossani
 */
public class EventList {
    private List<Event> eventList;
    
    public EventList() {
        eventList = new ArrayList<>();
    }
    
    public void addEvent(Event event) {
        int posicao = 0;
        
        for(int i = 0; i < eventList.size(); i++) {
            if(event.compareTo(eventList.get(i)) < 0) {
                posicao = i;
            }
        }
        
        eventList.add(posicao, event);
    }
    
    public Event removeHeadEvent() {
        return eventList.remove(0);
    }
}
