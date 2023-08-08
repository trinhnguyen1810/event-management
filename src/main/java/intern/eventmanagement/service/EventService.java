package intern.eventmanagement.service;

import intern.eventmanagement.entity.Event;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents();
    List<Event> getUpcomingEvents();
    List<Event> getPastEvents();
    Event getEventById(Long eventId);
    void saveEvent(Event event);
    void deleteEventById(Long eventId);

}

