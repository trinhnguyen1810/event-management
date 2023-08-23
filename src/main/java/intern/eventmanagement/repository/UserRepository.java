package intern.eventmanagement.repository;

import intern.eventmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    //@Query("SELECT u FROM User u WHERE CONCAT(u.firstName, ' ', u.lastName, ' ', u.email) LIKE %?1%")
    //public List<User> search(String keyword);
}
