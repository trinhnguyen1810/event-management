package intern.eventmanagement.service;

import intern.eventmanagement.entity.Event;
import intern.eventmanagement.entity.User;
import intern.eventmanagement.repository.EventRepository;
import intern.eventmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventService {
    List<Event> getAllEvents();
    List<Event> getUpcomingEvents();
    List<Event> getPastEvents();
    List<Event> searchEvents(String keyword);

    List<Event> getMyEvents();
    Event getEventById(Long eventId);

    void saveEvent(Event event, MultipartFile multipartFile) throws IOException;

    void deleteEventById(Long eventId);
    boolean removeAttendee(Long eventId, String username);
    boolean addAttendee(Long eventId, String username);

    User getCurrentUser();









}

