/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.form;

import java.util.ArrayList;
import java.util.Scanner;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import acs.fluffy.domain.Fingerprint;

/**
 * A form object that is used to obtain form data and parse it to make fingerprints
 * @author tonykovanen
 */
public class MeasurementForm {
    private LocalizationType type;
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
    
    public void setType(LocalizationType type) {
        this.type = type;
    }
    
    public LocalizationType getType() {
        return this.type;
    }
}
