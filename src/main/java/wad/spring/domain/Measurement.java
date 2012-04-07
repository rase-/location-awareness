/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.domain;

/**
 *
 * @author tonykovanen
 */
public class Measurement {
    private String macAddress;
    private int receivedSignalStrength;

    public Measurement(String macAddress, int receivedSignalStrength) {
        this.macAddress = macAddress;
        this.receivedSignalStrength = receivedSignalStrength;
    }

    public Measurement() {
    }

    public String getMacAddress() {
        return macAddress;
    }

    public int getReceivedSignalStrength() {
        return receivedSignalStrength;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setReceivedSignalStrength(int receivedSignalStrength) {
        this.receivedSignalStrength = receivedSignalStrength;
    }
    
}
