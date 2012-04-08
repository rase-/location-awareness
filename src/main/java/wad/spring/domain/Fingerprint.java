/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.domain;

import java.io.Serializable;

/**
 *
 * @author tonykovanen
 */
public class Fingerprint implements Serializable {
    private String macAddress;
    private int signalStrength;
    
    public Fingerprint(String macad, int rss) {
        this.macAddress = macad;
        this.signalStrength = rss;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = signalStrength;
    }
    
}
