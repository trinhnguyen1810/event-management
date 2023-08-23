package intern.eventmanagement.controllers;

import intern.eventmanagement.entity.Event;
import intern.eventmanagement.entity.User;
import intern.eventmanagement.service.EventService;
import intern.eventmanagement.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping(value = { "/showEvents" })
    public ModelAndView showEvents(@RequestParam(name = "tag", required = false) String tag) {
        ModelAndView mav = new ModelAndView("list-events");
        mav.addObject("pastStatus", Event.EventStatus.PAST);
        List<Event> eventList = eventService.getAllEvents();
        if ("upcoming".equals(tag)) {
            eventList = eventService.getUpcomingEvents();
        } else if ("past".equals(tag)) {
            eventList = eventService.getPastEvents();
        } else if ("my".equals(tag)) {
            eventList = eventService.getMyEvents();
        }
        mav.addObject("events", eventList);
        return mav;
    }


    @GetMapping(value = { "/showAllEventsUsers" })
    public ModelAndView showAllEventsUsers() {
        ModelAndView mav = new ModelAndView("list-events-users");
        List<Event> eventList = eventService.getAllEvents();
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
    public ModelAndView eventPost(@RequestParam Long eventId) {
        ModelAndView mav = new ModelAndView("post");
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

    @GetMapping("/event/{eventId}")
    public String getEventDetails(@PathVariable Long eventId, Model model) {
        Event event = eventService.getEventById(eventId);
        User currentUser = eventService.getCurrentUser();
        boolean userRsvped = event.getAttendees().contains(currentUser);

        model.addAttribute("event", event);
        model.addAttribute("userRsvped", userRsvped);

        return  "list-events";
    }


}
