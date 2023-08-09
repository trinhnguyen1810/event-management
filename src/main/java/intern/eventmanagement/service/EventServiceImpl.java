package intern.eventmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import intern.eventmanagement.entity.Event;
import intern.eventmanagement.repository.EventRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        events.forEach(Event::updateEventStatus);
        List<Event> allEvents = events.stream()
                .sorted(Comparator.comparing(Event::getDateEvent).reversed())
                .collect(Collectors.toList());
        return allEvents;
    }
    @Override
    public List<Event> getPastEvents() {
        List<Event> events = eventRepository.findAll();
        events.forEach(Event::updateEventStatus);

        List<Event> pastEvents = events.stream()
                .filter(event -> event.getEventStatus() == Event.EventStatus.PAST)
                .sorted(Comparator.comparing(Event::getDateEvent).reversed())
                .collect(Collectors.toList());

        return pastEvents;
    }

    @Override
    public List<Event> getUpcomingEvents() {
        List<Event> events = eventRepository.findAll();
        List<Event> upcomingEvents = events.stream()
                .filter(event -> event.getEventStatus() == Event.EventStatus.UPCOMING)
                .sorted(Comparator.comparing(Event::getDateEvent))
                .collect(Collectors.toList());
        events.forEach(Event::updateEventStatus);
        return upcomingEvents;
    }


    @Override
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    @Override
    public void saveEvent(Event event, MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        event.setPhoto(fileName);
        eventRepository.save(event);
    }

    @Override
    public void deleteEventById(Long eventId) {
        eventRepository.deleteById(eventId);
    }
}
