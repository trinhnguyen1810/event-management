package intern.eventmanagement.service;

import intern.eventmanagement.entity.Event;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventService {
    List<Event> getAllEvents();
    List<Event> getUpcomingEvents();
    List<Event> getPastEvents();
    Event getEventById(Long eventId);

    void saveEvent(Event event, MultipartFile multipartFile) throws IOException;

    void deleteEventById(Long eventId);

}

