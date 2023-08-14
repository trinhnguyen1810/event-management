package intern.eventmanagement.entity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.time.ZoneId;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;




@Entity
@Table(name ="tbl_events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String eventName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date dateEvent;
    @Column(nullable = false)
    private String timeEvent;
    @Transient
    private Event.EventStatus eventStatus;

    private String dateCreate;
    @Column(nullable = false)
    private String location;

    @ElementCollection
    private Set<String> categories = new HashSet<>();

    private String urlLink;
    @Column(nullable = false)
    private String organizer;

    @Column(nullable = true, length = 64)
    private String photo;

    @Column(length = 4000,nullable=false)
    private String description;

    @ManyToMany
    private Set<User> attendees = new HashSet<>();

    @Transient
    private String photosImagePath;

    public Event(String eventName, Date dateEvent, String timeEvent, String location,
                 Set<String> categories, String photo, String urlLink, String organizer, String description) {
        this.eventName = eventName;
        this.timeEvent = timeEvent;
        this.dateEvent = dateEvent;
        this.dateCreate = this.setDateCreate();
        this.location = location;
        this.categories = categories;
        this.photo = photo;
        this.urlLink = urlLink;
        this.organizer = organizer;
        this.description = description;
        this.photosImagePath = getPhotosImagePath();
    }

    public enum EventStatus {
        UPCOMING, PAST
    }

    public void updateEventStatus() {
        LocalDate currentDate = LocalDate.now();
        LocalDate eventDate = dateEvent.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (eventDate.isEqual(currentDate)) {
            LocalTime currentTime = LocalTime.now();
            LocalTime eventTime = LocalTime.parse(timeEvent, DateTimeFormatter.ofPattern("HH:mm"));

            if (eventTime.isAfter(currentTime)) {
                eventStatus = EventStatus.UPCOMING;
            } else {
                eventStatus = EventStatus.PAST;
            }
        } else if (eventDate.isAfter(currentDate)) {
            eventStatus = EventStatus.UPCOMING;
        } else {
            eventStatus = EventStatus.PAST;
        }
    }
    @Transient
    public String getPhotosImagePath() {
        if (photo == null || id == null) return null;

        return "event-photo/" + id + "/" + this.photo;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getDateEvent() {
        return dateEvent;
    }

    public String setDateCreate() {
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateObj.format(formatter);
        return date;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateEvent(Date dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getTimeEvent() {
        return timeEvent;
    }

    public void setTimeEvent(String timeEvent) {
        this.timeEvent = timeEvent;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(Set<User> attendees) {
        this.attendees = attendees;
    }

    // remember to implement logic for date type


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", dateEvent=" + dateEvent +
                ", dateCreate=" + dateCreate +
                ", location='" + location + '\'' +
                ", categories=" + categories +
                ", urlLink='" + urlLink + '\'' +
                ", organizer='" + organizer + '\'' +
                ", description='" + description + '\'' +
                ", attendees=" + attendees +
                '}';
    }
}

