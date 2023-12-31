package intern.eventmanagement.repository;

import intern.eventmanagement.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findById(Long Id);
    @Query("SELECT e FROM Event e WHERE e.eventName LIKE %?1%")
    public List<Event> search(String keyword);

}
