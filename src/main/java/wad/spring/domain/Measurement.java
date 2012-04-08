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
    private List<String> macAddress;
    private List<Integer> receivedSignalStrength;
    @Temporal(TemporalType.TIMESTAMP)
    private Date measureTime;

    public Long getId() {
        return id;
    }

    public List<String> getMacAddress() {
        return macAddress;
    }

    public Date getMeasureTime() {
        return measureTime;
    }

    public List<Integer> getReceivedSignalStrength() {
        return receivedSignalStrength;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMacAddress(List<String> macAddress) {
        this.macAddress = macAddress;
    }

    public void setMeasureTime(Date measureTime) {
        this.measureTime = measureTime;
    }

    public void setReceivedSignalStrength(List<Integer> receivedSignalStrength) {
        this.receivedSignalStrength = receivedSignalStrength;
    }

    
}