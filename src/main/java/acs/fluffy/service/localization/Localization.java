/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.service.localization;

import java.util.List;
import acs.fluffy.domain.Fingerprint;
import acs.fluffy.domain.Place;

/**
 * Interface for localization command
 * @author tonykovanen
 */
public interface Localization {
    
    /**
     * Positions user by a defined measure and returns closest place
     * @param places Places to be matched
     * @param userPrints Fingerprings of user
     * @return Closest place according to some measure
     */
    Place localize(List<Place> places, List<Fingerprint> userPrints);
}
