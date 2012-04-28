/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.service.localization;

import java.util.List;
import acs.fluffy.domain.Fingerprint;
import acs.fluffy.domain.Place;

/**
 *
 * @author tonykovanen
 */
public interface Localization {
    Place localize(List<Place> places, List<Fingerprint> userPrints);
}
