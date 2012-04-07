package wad.spring.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Queue;
import javax.persistence.*;

@Entity(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String name;
    @Column(unique = true)
    private String username;
    private String password;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Role> roles;
    @ManyToOne
    private List<User> pendingFriendRequests;
    
    @OneToMany
    private List<User> friends;
    
    @ManyToMany
    private Queue<Place> history;
    
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

    public List<User> getPendingFriendRequests() {
        return pendingFriendRequests;
    }

    public void setPendingFriendRequests(List<User> pendingFriendRequests) {
        this.pendingFriendRequests = pendingFriendRequests;
    }

    public Queue<Place> getHistory() {
        return history;
    }

    public void setHistory(Queue<Place> history) {
        this.history = history;
    }
    
}