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
public class HyperbolicFingerprint implements Serializable {
    private String firstMacAddress;
    private String secondMacAddress;
    private double firstSignalStrength;
    private double secondSignalStrength;
    private double logarithmicRatio;
    
    public HyperbolicFingerprint(Fingerprint print1, Fingerprint print2) {
        this.firstMacAddress = print1.getMacAddress();
        this.secondMacAddress = print2.getMacAddress();
        this.firstSignalStrength = print1.getSignalStrength();
        this.secondSignalStrength = print2.getSignalStrength();
        this.logarithmicRatio = Math.log10(this.firstSignalStrength / this.secondSignalStrength);
    }

    public String getFirstMacAddress() {
        return firstMacAddress;
    }

    public double getFirstSignalStrength() {
        return firstSignalStrength;
    }

    public double getLogarithmicRatio() {
        return logarithmicRatio;
    }

    public String getSecondMacAddress() {
        return secondMacAddress;
    }

    public double getSecondSignalStrength() {
        return secondSignalStrength;
    }

    public void setFirstMacAddress(String firstMacAddress) {
        this.firstMacAddress = firstMacAddress;
    }

    public void setFirstSignalStrength(double firstSignalStrength) {
        this.firstSignalStrength = firstSignalStrength;
    }

    public void setLogarithmicRatio(double logarithmicRatio) {
        this.logarithmicRatio = logarithmicRatio;
    }

    public void setSecondMacAddress(String secondMacAddress) {
        this.secondMacAddress = secondMacAddress;
    }

    public void setSecondSignalStrength(double secondSignalStrength) {
        this.secondSignalStrength = secondSignalStrength;
    }
    
}
