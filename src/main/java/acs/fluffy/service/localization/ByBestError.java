/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.service.localization;

import java.util.ArrayList;
import java.util.List;
import acs.fluffy.domain.Fingerprint;
import acs.fluffy.domain.HyperbolicFingerprint;
import acs.fluffy.domain.Measurement;
import acs.fluffy.domain.Place;

/**
 *
 * @author tonykovanen
 */
public class ByBestError extends AbstractLocalization {

    @Override
    public Place localize(List<Place> places, List<Fingerprint> userPrints) {
        double smallestError = Double.MAX_VALUE;
        Place leastErraneousPlace = places.get(0);
        for (Place p : places) {
            List<Measurement> measurements = p.getMeasurements();
            // We want to match user fingerprints for each hyperbolic measurement in each place
            // We then want to calculate the euclidean distance between these two
            for (Measurement m : measurements) {
                double placeError = matchPrintsAndMakeHyperbolicAndCalculateError(userPrints, m.getFingerprints());
                if (placeError < smallestError) {
                    smallestError = placeError;
                    leastErraneousPlace = p;
                }
            }

        }
        return leastErraneousPlace;
    }

    // Error function is euclidean distance
    @Override
    protected double calculateError(List<HyperbolicFingerprint> userPrints, List<HyperbolicFingerprint> referencePrints) {
        double sum = 0;
        for (int i = 0; i < userPrints.size(); i++) {
            sum += Math.pow(userPrints.get(i).getLogarithmicRatio() - referencePrints.get(i).getLogarithmicRatio(), 2);
        }
        return Math.sqrt(sum);
    }
}
