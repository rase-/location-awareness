/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.spring.domain.Measurement;

/**
 *
 * @author tonykovanen
 */
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    
}
