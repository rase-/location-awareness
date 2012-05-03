/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.service.localization;

import java.util.List;
import acs.fluffy.domain.Fingerprint;
import acs.fluffy.domain.HyperbolicFingerprint;
import acs.fluffy.domain.Measurement;
import acs.fluffy.domain.Place;

/**
 * Command object used to position user by smallest error
 * @author tonykovanen
 */
public class ByBestError extends AbstractLocalization {
    /**
     * Localizes user by finding place that best matches user's prints (smallest error) and returns the place it belongs to
     * @param places Places to be matched
     * @param userPrints Fingerprints of user to be matched
     * @return The "closest" place
     */
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

    /**
     * Calculates the Euclidean distance between two lists of hyperbolic fingerprints (matched)
     * @param userPrints Prints of user
     * @param referencePrints Prints from database
     * @return Euclidean distance
     */
    @Override
    protected double calculateError(List<HyperbolicFingerprint> userPrints, List<HyperbolicFingerprint> referencePrints) {
        double sum = 0;
        for (int i = 0; i < userPrints.size(); i++) {
            sum += Math.pow(userPrints.get(i).getLogarithmicRatio() - referencePrints.get(i).getLogarithmicRatio(), 2);
        }
        return Math.sqrt(sum);
    }
}
