/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author tonykovanen
 */

@Entity
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private List<Fingerprint> fingerprints;
    @Temporal(TemporalType.TIMESTAMP)
    private Date measureTime;

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

    

    
}