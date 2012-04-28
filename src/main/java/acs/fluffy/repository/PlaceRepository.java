/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import acs.fluffy.domain.Place;

/**
 * DAO for Places. JpaRepository provides basic queries.
 * @author tonykovanen
 */
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Place findByName(String name);
}
