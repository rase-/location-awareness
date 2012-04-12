/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 *
 * @author tonykovanen
 */
@Entity
public class Place implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(unique = true)
    @Pattern(regexp="^[a-zA-Z0-9äöüÄÖÜ'-]*$", message="The name should only contains characters a-z, A-Z, 0-9, äöüÄÖÜ'-")
    private String name;
    @Pattern(regexp="^[^<>%$]*$", message="The description should not contain <, >, % or $ characters")
    private String description;
    
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Measurement> measurements;

    public Long getId() {
        return id;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
   
    
}
