package intern.eventmanagement.controllers;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import intern.eventmanagement.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import intern.eventmanagement.entity.User;

@RestController
public class AttendeeController {

    @Autowired
    private EventService eventService;

    @PostMapping("/addAttendee")
    public ResponseEntity<String> addAttendee(@RequestParam Long eventId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        boolean success = eventService.addAttendee(eventId, user);
        if (success) {
            return ResponseEntity.ok("User added to attendees");
        } else {
            return ResponseEntity.badRequest().body("Failed to add user to attendees");
        }
    }

    @PostMapping("/removeAttendee")
    public ResponseEntity<String> removeAttendee(@RequestParam Long eventId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        boolean success = eventService.removeAttendee(eventId, user);
        if (success) {
            return ResponseEntity.ok("User removed from attendees");
        } else {
            return ResponseEntity.badRequest().body("Failed to remove user from attendees");
        }
    }
}