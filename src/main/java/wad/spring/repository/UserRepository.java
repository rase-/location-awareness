package wad.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.spring.domain.User;
/**
 * DAO for Users. JpaRepository provides basic queries.
 * @author tonykovanen
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * A method to find user by username is defined according to JparRepository standard,
     * so implementation is automatic
     * @param username Username of user to be found
     * @return The user that was found or null if it wasn't found
     */
    public User findByUsername(String username);
}
