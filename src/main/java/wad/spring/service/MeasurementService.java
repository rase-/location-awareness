/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import java.util.List;
import wad.spring.domain.Measurement;

/**
 *
 * @author tonykovanen
 */
public interface MeasurementService {
    Measurement findOne(Long id);
    List<Measurement> findAll();
    void save(Measurement measurement);
    void deleteById(Long placeId, Long measurementId);
}
