/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.domain;

import java.io.Serializable;

/**
 * A class that stores mac address signal strength pairs
 *
 * @author tonykovanen
 */
public class Fingerprint implements Serializable, Comparable {

    private String macAddress;
    private double signalStrength;

    public Fingerprint(String macad, double rss) {
        this.macAddress = macad;
        this.signalStrength = rss;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public double getSignalStrength() {
        return signalStrength;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setSignalStrength(double signalStrength) {
        this.signalStrength = signalStrength;
    }

    @Override
    public int compareTo(Object t) {
        if (t instanceof Fingerprint) {
            Fingerprint f = (Fingerprint) t;
            return this.macAddress.compareTo(f.getMacAddress());
        } 
        return this.macAddress.compareTo("");
    }
}
