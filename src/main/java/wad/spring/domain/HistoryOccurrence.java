/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Instane of a place at a given time. Each user as a list of HistoryOccurrences
 * @author tonykovanen
 */
@Entity
public class HistoryOccurrence implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date measureTime;
    @OneToOne
    Place place;

    public Long getId() {
        return id;
    }

    public Place getPlace() {
        return place;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
    
    public void setMeasureTime(Date time) {
        this.measureTime = time;
    }
    
    public Date getMeasureTime() {
        return this.measureTime;
    }
    
}
