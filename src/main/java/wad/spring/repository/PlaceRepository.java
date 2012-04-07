/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.spring.domain.Place;

/**
 *
 * @author tonykovanen
 */
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Place findByName(String name);
}