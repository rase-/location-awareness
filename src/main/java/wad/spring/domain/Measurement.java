/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * A measurement consists of a list of hyperbolic fingerprints associated with the measurement, a measuretime and a place that it is linked to
 * @author tonykovanen
 */

@Entity
public class Measurement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private List<Fingerprint> fingerprints;
    @Temporal(TemporalType.TIMESTAMP)
    private Date measureTime;
    @ManyToOne
    private Place place;

    public boolean equals(Object o) {
        if (o instanceof Measurement) {
            Measurement m = (Measurement) o;
            if (m.getId() == this.id) {
                return true;
            }
            return false;
        }
        return false;
    }
    
    public Long getId() {
        return id;
    }

    public List<Fingerprint> getFingerprints() {
        return this.fingerprints;
    }
    
    public void setFingerprints(List<Fingerprint> fingerprints) {
        this.fingerprints = fingerprints;
    }

    public Date getMeasureTime() {
        return measureTime;
    }


    public void setId(Long id) {
        this.id = id;
    }
    
    public void setMeasureTime(Date measureTime) {
        this.measureTime = measureTime;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    
}