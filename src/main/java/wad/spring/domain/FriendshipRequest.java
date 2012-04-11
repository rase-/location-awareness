/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author tonykovanen
 */
@Entity
public class FriendshipRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @ManyToOne
    private User sender;

    @Override
    public boolean equals(Object o) {
        if (o instanceof FriendshipRequest) {
            FriendshipRequest f = (FriendshipRequest) o;
            if (f.getId() == this.id) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    public Long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
    
    
}
