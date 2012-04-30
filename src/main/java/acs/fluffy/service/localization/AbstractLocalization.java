/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.service.localization;

import java.util.ArrayList;
import java.util.List;
import acs.fluffy.domain.Fingerprint;
import acs.fluffy.domain.HyperbolicFingerprint;
import acs.fluffy.domain.Place;

/**
 * Abstract tool class for Hyperbolic prints matching command objects
 * @author tonykovanen
 */
public abstract class AbstractLocalization implements Localization {
    /**
     * Localizes user by measurements and received place list
     */
    @Override
    public abstract Place localize(List<Place> places, List<Fingerprint> userPrints);
    
    /**
     * Calculates error according to hyperbolic user prints and reference
     * @param userPrints User prints
     * @param referencePrints Reference prints
     * @return The calculated errro
     */
    protected abstract double calculateError(List<HyperbolicFingerprint> userPrints, List<HyperbolicFingerprint> referencePrints);
    
    /**
     * Makes fingerprints hyperbolic by taking each unique pair of fingerprints in the list and tranforming them to hyperbolic prints
     * @param regular Regular prints
     * @return Hyperbolic prints
     */
    protected List<HyperbolicFingerprint> makeHyperbolic(List<Fingerprint> regular) {
        ArrayList<HyperbolicFingerprint> hyperbolic = new ArrayList<HyperbolicFingerprint>();
        for (int i = 1; i < regular.size(); i++) {
            for (int j = 0; j < i; j++) {
                hyperbolic.add(new HyperbolicFingerprint(regular.get(i), regular.get(j)));
            }

        }
        return hyperbolic;
    }
    
    /**
     * Matches two sets of prints to contain all missing mac addresses with default value -100 and returns the error between them
     * @param userPrints Prints of user
     * @param databasePrints Prints from database
     * @return 
     */
    protected double matchPrintsAndMakeHyperbolicAndCalculateError(List<Fingerprint> userPrints, List<Fingerprint> databasePrints) {
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

        return calculateError(makeHyperbolic(matchedUserPrints), makeHyperbolic(matchedDatabasePrints));
    }
}
