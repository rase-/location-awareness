/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.service.localization;

import java.util.List;
import acs.fluffy.domain.Fingerprint;
import acs.fluffy.domain.Measurement;
import acs.fluffy.domain.Place;

/**
 *
 * @author tonykovanen
 */
public class ByErrorAverage extends ByBestError {
    
    @Override
    public Place localize(List<Place> places, List<Fingerprint> userPrints) {
        double smallestError = Double.MAX_VALUE;
        Place leastErraneousPlace = places.get(0);
        for (Place p : places) {
            double average = 0;
            List<Measurement> measurements = p.getMeasurements();
            // We want to match user fingerprints for each hyperbolic measurement in each place
            // We then want to calculate the euclidean distance between these two
            for (Measurement m : measurements) {
                double placeError = matchPrintsAndMakeHyperbolicAndCalculateError(userPrints, m.getFingerprints());
                average += placeError;
            }
            average /= measurements.size();
            if (average < smallestError) {
                smallestError = average;
                leastErraneousPlace = p;
            }

        }
        return leastErraneousPlace;
    }
}
