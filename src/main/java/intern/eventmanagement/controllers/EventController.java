package intern.eventmanagement.controllers;

import intern.eventmanagement.entity.Event;
import intern.eventmanagement.service.EventService;
import intern.eventmanagement.service.MyUserDetails;
import intern.eventmanagement.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping(value = { "/showEvents" })
    public ModelAndView showEvents(@RequestParam(name = "tag", required = false) String tag,
                                   @AuthenticationPrincipal MyUserDetails userDetails) {
        ModelAndView mav = new ModelAndView("list-events");
        mav.addObject("pastStatus", Event.EventStatus.PAST);
        String currentUsername = userDetails.getUsername();
        mav.addObject("currentUsername", currentUsername);
        mav.addObject("eventService", eventService);
        List<Event> eventList;

        if ("upcoming".equals(tag)) {
            eventList = eventService.getUpcomingEvents();
        } else if ("past".equals(tag)) {
            eventList = eventService.getPastEvents();
        } else if ("my".equals(tag)) {
            String currentUserEmail = userDetails.getUsername();
            eventList = eventService.getMyEvents(currentUserEmail);
        } else {
            eventList = eventService.getAllEvents();
        }
        mav.addObject("events", eventList);
        return mav;
    }

    @GetMapping("/addEventForm")
    public ModelAndView addEventForm() {
        ModelAndView mav = new ModelAndView("add-event-form");
        Event newEvent = new Event();
        mav.addObject("event", newEvent);
        return mav;
    }

    @GetMapping("/eventPost")
    public ModelAndView eventPost(@RequestParam Long eventId,
                                  @AuthenticationPrincipal MyUserDetails userDetails) {
        ModelAndView mav = new ModelAndView("post");
        String currentUsername = userDetails.getUsername();
        List<Event> eventList = eventService.getUpcomingEvents();
        mav.addObject("currentUsername", currentUsername);
        mav.addObject("eventService", eventService);
        mav.addObject("eventList",eventList);
        Event event = eventService.getEventById(eventId);
        mav.addObject("event", event);
        return mav;
    }

    @PostMapping("/saveEvent")
    public String saveEvent(@ModelAttribute Event event, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        eventService.saveEvent(event,multipartFile);
        String uploadDir = "event-photo/" + event.getId();
        String fileName = event.getPhoto();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/showEvents";
    }

    @GetMapping("/showUpdateEvent")
    public ModelAndView showUpdateForm(@RequestParam Long eventId) {
        ModelAndView mav = new ModelAndView("add-event-form");
        Event event = eventService.getEventById(eventId);
        mav.addObject("event", event);
        return mav;
    }
    @GetMapping("/deleteEvent")
    public String deleteEvent(@RequestParam Long eventId) {
        eventService.deleteEventById(eventId);
        return "redirect:/showEvents";
    }

    @PostMapping("/rsvp")
    public String rsvp(@RequestParam Long eventId, @AuthenticationPrincipal MyUserDetails userDetails) {
        String username = userDetails.getUsername();
        boolean isUserRsvped = eventService.isUserRsvped(eventId, username);
        if (isUserRsvped) {
            boolean removed = eventService.removeAttendee(eventId, username);

        } else {
            boolean added = eventService.addAttendee(eventId, username);
        }
        return "redirect:/showEvents";
    }

    @PostMapping("/rsvpPost")
    public String rsvpMy(@RequestParam Long eventId, @AuthenticationPrincipal MyUserDetails userDetails) {
        String username = userDetails.getUsername();
        boolean isUserRsvped = eventService.isUserRsvped(eventId, username);
        if (isUserRsvped) {
            boolean removed = eventService.removeAttendee(eventId, username);

        } else {
            boolean added = eventService.addAttendee(eventId, username);
        }
        return "redirect:/eventPost?eventId=" + eventId;
    }

    @GetMapping("/searchEvents")
    public ModelAndView searchEvents(@RequestParam String keyword) {
        List<Event> matchingEvents = eventService.searchEvents(keyword);

        ModelAndView mav = new ModelAndView("list-events");
        mav.addObject("events", matchingEvents);
        return mav;
    }


}
