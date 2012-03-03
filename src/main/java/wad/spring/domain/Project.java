/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author tonykovanen
 */
@Entity
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String name;
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }
    public int getLikes() {
        return likingStudents.size();
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
    
    @ManyToOne
    private User customer;

    public User getCustomer() {
        return customer;
    }

    public List<User> getLikingStudents() {
        return likingStudents;
    }

    public Long getId() {
        return id;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void setLikingStudents(List<User> likingStudents) {
        this.likingStudents = likingStudents;
    }

    public void setId(Long projectId) {
        this.id = projectId;
    }
    
    @ManyToMany
    private List<User> likingStudents;
}
