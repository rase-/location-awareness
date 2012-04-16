package wad.spring.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Queue;
import javax.persistence.*;
/**
 * A user in the system has a username, a password and n roles. It also has a list of friends, friendrequests and a history of places (HistoryOccurrence).
 * @author tonykovanen
 */
@Entity(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String name;
    /**
     * Is unique
     */
    @Column(unique = true)
    private String username;
    private String password;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Role> roles;
    
    @OneToMany
    private List<User> friends;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<FriendshipRequest> receivedFriendRequests;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<HistoryOccurrence> history;
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User u = (User) o;
            if (this.id == u.getId()) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<HistoryOccurrence> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryOccurrence> history) {
        this.history = history;
    }

    public List<FriendshipRequest> getReceivedFriendRequests() {
        return receivedFriendRequests;
    }

    public void setReceivedFriendRequests(List<FriendshipRequest> receivedFriendRequests) {
        this.receivedFriendRequests = receivedFriendRequests;
    }
    
}