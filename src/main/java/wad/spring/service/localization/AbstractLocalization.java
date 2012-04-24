/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service.localization;

import java.util.ArrayList;
import java.util.List;
import wad.spring.domain.Fingerprint;
import wad.spring.domain.HyperbolicFingerprint;
import wad.spring.domain.Place;

/**
 *
 * @author tonykovanen
 */
public abstract class AbstractLocalization implements Localization {
    public abstract Place localize(List<Place> places, List<Fingerprint> userPrints);
    
    protected abstract double calculateError(List<HyperbolicFingerprint> userPrints, List<HyperbolicFingerprint> referencePrints);
    
    protected List<HyperbolicFingerprint> makeHyperbolic(List<Fingerprint> regular) {
        ArrayList<HyperbolicFingerprint> hyperbolic = new ArrayList<HyperbolicFingerprint>();
        for (int i = 1; i < regular.size(); i++) {
            for (int j = 0; j < i; j++) {
                hyperbolic.add(new HyperbolicFingerprint(regular.get(i), regular.get(j)));
            }

        }
        return hyperbolic;
    }
    
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
