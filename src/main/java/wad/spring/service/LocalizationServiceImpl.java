/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.spring.domain.*;
import wad.spring.repository.PlaceRepository;
import wad.spring.repository.UserRepository;

/**
 *
 * @author tonykovanen
 */

@Service
public class LocalizationServiceImpl implements LocalizationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PlaceRepository placeRepository;

    @Override
    @Transactional
    public void localizeByBestError(String username, MeasurementForm measurementform) {
        User user = userRepository.findByUsername(username);
        ArrayList<Fingerprint> userPrints = measurementform.makeFingerprints();
        // We sort the prints to make matching easier
        Collections.sort(userPrints);
        List<Place> places = placeRepository.findAll();

        //If there are no places we won't do anything
        if (places.isEmpty()) {
            return;
        }

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

        while (user.getHistory().size() >= 10) {
            user.getHistory().remove(0);
        }
        
        HistoryOccurrence occurrence = new HistoryOccurrence();
        occurrence.setPlace(leastErraneousPlace);
        occurrence.setMeasureTime(new Date());
        user.getHistory().add(occurrence);
        userRepository.save(user);
    }

    /**
     * Calculates euclidean distance between user's hyperbolic prints and one
     * reference measurement
     *
     * @param userPrints User's prints
     * @param reference One measurement in a place
     * @return Squared error
     */
    private double euclideanDistance(List<HyperbolicFingerprint> userPrints, List<HyperbolicFingerprint> reference) {
        double sum = 0;
        for (int i = 0; i < userPrints.size(); i++) {
            sum += Math.pow(userPrints.get(i).getLogarithmicRatio() - reference.get(i).getLogarithmicRatio(), 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * Makes a hyperbolic fingerprint of each unique pair of prints
     *
     * @param regular Regular fingerprints as list
     * @return Hyperbolic fingerprints as list
     */
    private List<HyperbolicFingerprint> makeHyperbolic(List<Fingerprint> regular) {
        ArrayList<HyperbolicFingerprint> hyperbolic = new ArrayList<HyperbolicFingerprint>();
        for (int i = 1; i < regular.size(); i++) {
            for (int j = 0; j < i; j++) {
                hyperbolic.add(new HyperbolicFingerprint(regular.get(i), regular.get(j)));
            }

        }
        return hyperbolic;
    }

    /**
     * Matches prints, then makes them hyperbolic and calculates euclidean
     * distance
     *
     * @param userPrints Fingprints of user
     * @param databasePrints Fingerprints of one measurement
     * @return Euclidean distance between the two prints
     */
    private double matchPrintsAndMakeHyperbolicAndCalculateError(List<Fingerprint> userPrints, List<Fingerprint> databasePrints) {
        int i = 0;
        int j = 0;
        ArrayList<Fingerprint> matchedUserPrints = new ArrayList<Fingerprint>();
        ArrayList<Fingerprint> matchedDatabasePrints = new ArrayList<Fingerprint>();

        while (i < userPrints.size() || j < databasePrints.size()) {
            if (i == userPrints.size()) {
                matchedUserPrints.add(new Fingerprint(databasePrints.get(j).getMacAddress(), -100));
                matchedDatabasePrints.add(databasePrints.get(j));
                j++;
            } else if (j == databasePrints.size()) {
                matchedUserPrints.add(userPrints.get(i));
                matchedDatabasePrints.add(new Fingerprint(userPrints.get(i).getMacAddress(), -100));
                i++;
            } else if (userPrints.get(i).getMacAddress().equals(databasePrints.get(j).getMacAddress())) {
                matchedUserPrints.add(userPrints.get(i));
                matchedDatabasePrints.add(databasePrints.get(j));
                i++;
                j++;

            } else if (userPrints.get(i).compareTo(databasePrints.get(j)) < 0) {
                matchedUserPrints.add(userPrints.get(i));
                matchedDatabasePrints.add(new Fingerprint(userPrints.get(i).getMacAddress(), -100));
                i++;

            } else {
                matchedUserPrints.add(new Fingerprint(databasePrints.get(j).getMacAddress(), -100));
                matchedDatabasePrints.add(databasePrints.get(j));
                j++;
            }

        }

        return euclideanDistance(makeHyperbolic(matchedUserPrints), makeHyperbolic(matchedDatabasePrints));
    }

    @Override
    @Transactional
    public void localizeByErrorAverage(String username, MeasurementForm measurementform) {
        User user = userRepository.findByUsername(username);
        ArrayList<Fingerprint> userPrints = measurementform.makeFingerprints();
        // We sort the prints to make matching easier
        Collections.sort(userPrints);
        List<Place> places = placeRepository.findAll();

        //If there are no places we won't do anything
        if (places.isEmpty()) {
            return;
        }

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

        while (user.getHistory().size() >= 10) {
            user.getHistory().remove(0);
        }
        HistoryOccurrence occurrence = new HistoryOccurrence();
        occurrence.setPlace(leastErraneousPlace);
        occurrence.setMeasureTime(new Date());
        user.getHistory().add(occurrence);
        userRepository.save(user);
    }
}
