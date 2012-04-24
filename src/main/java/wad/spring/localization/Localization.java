/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.localization;

import java.util.List;
import wad.spring.domain.Fingerprint;
import wad.spring.domain.Place;

/**
 *
 * @author tonykovanen
 */
public interface Localization {
    Place localize(List<Place> places, List<Fingerprint> userPrints);
}
