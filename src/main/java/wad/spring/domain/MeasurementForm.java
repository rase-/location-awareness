/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.domain;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author tonykovanen
 */
public class MeasurementForm {
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
