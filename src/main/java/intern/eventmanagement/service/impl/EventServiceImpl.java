package intern.eventmanagement.service.impl;

import intern.eventmanagement.entity.Event;
import intern.eventmanagement.repository.EventRepository;
import intern.eventmanagement.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import intern.eventmanagement.entity.User;
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
    @Override
    public boolean addAttendee(Long eventId, User user) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event != null) {
            event.getAttendees().add(user);
            eventRepository.save(event);
            return true;
        }
        return false;
    }
    @Override

    public boolean removeAttendee(Long eventId, User user) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event != null) {
            event.getAttendees().remove(user);
            eventRepository.save(event);
            return true;
        }
        return false;
    }


}
