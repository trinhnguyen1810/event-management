package intern.eventmanagement.controllers;

import intern.eventmanagement.service.EventService;
import intern.eventmanagement.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class AttendeeController {

    @Autowired
    private EventService eventService;
    @Transactional
    @PostMapping("/addAttendee")
    public ResponseEntity<String> addAttendee(@RequestParam Long eventId, @AuthenticationPrincipal MyUserDetails userDetails) {
        //String username = (User) authentication.getPrincipal();
        String username = userDetails.getUsername();
        boolean success = eventService.addAttendee(eventId, username);
        if (success) {
            return ResponseEntity.ok("User added to attendees");
        } else {
            return ResponseEntity.badRequest().body("Failed to add user to attendees");
        }
    }

    @PostMapping("/removeAttendee")
    public ResponseEntity<String> removeAttendee(@RequestParam Long eventId, @AuthenticationPrincipal MyUserDetails userDetails) {
        //User user = (User) authentication.getPrincipal();
        String username = userDetails.getUsername();
        boolean success = eventService.removeAttendee(eventId, username);
        if (success) {
            return ResponseEntity.ok("User removed from attendees");
        } else {
            return ResponseEntity.badRequest().body("Failed to remove user from attendees");
        }
    }

}