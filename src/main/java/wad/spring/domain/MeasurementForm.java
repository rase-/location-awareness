/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.domain;

import java.util.ArrayList;
import java.util.Scanner;
import javax.validation.constraints.Pattern;

/**
 * A form object that is used to obtain form data and parse it to make fingerprints
 * @author tonykovanen
 */
public class MeasurementForm {
    //@Pattern(regexp="^([a-zA-Z0-9:-]+)(\\w)([-+]?[0-9]*\\.?[0-9]+$)", message="The measurement should consist of mac address signal strength pairs written: address <space> rss<linebreak>address <space> rss<linebreak> etc")
    private String measurements;

    public String getMeasurements() {
        return measurements;
    }

    public void setMeasurements(String measurements) {
        this.measurements = measurements;
    }
    public ArrayList<Fingerprint> makeFingerprints() {
        ArrayList<Fingerprint> fingerprints = new ArrayList<Fingerprint>();
        String input = this.getMeasurements();
        Scanner scanner = new Scanner(input);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] individual = line.split(" ");
            fingerprints.add(new Fingerprint(individual[0], Integer.parseInt(individual[1])));
        }
        return fingerprints;
    }
}
