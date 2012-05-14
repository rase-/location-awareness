/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.domain;

import java.io.Serializable;

/**
 * A class that stores mac address signal strength pairs
 *
 * @author tonykovanen
 */
public class Fingerprint implements Serializable, Comparable {

    private String macAddress;
    private double signalStrength;

    public Fingerprint(String macAddress, double receivedSignalStrength) {
        this.macAddress = macAddress;
        this.signalStrength = receivedSignalStrength;
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

    @Override
    public String toString() {
        return macAddress + " " + signalStrength;
    }
}
