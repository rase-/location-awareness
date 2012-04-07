/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import java.util.List;
import wad.spring.domain.Place;

/**
 *
 * @author tonykovanen
 */
public interface PlaceService {
    void save(Place place);
    List<Place> findAll();
    Place findOne(Long id);
    Place findByName(String name);
}
