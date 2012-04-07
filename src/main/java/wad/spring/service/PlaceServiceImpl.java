/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.spring.domain.Place;
import wad.spring.repository.PlaceRepository;

/**
 *
 * @author tonykovanen
 */
@Service
public class PlaceServiceImpl implements PlaceService {
    @Autowired
    PlaceRepository placeRepository;
    
    @Override
    @Transactional
    public void save(Place place) {
        placeRepository.save(place);
    }

    @Override
    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    @Override
    public Place findOne(Long id) {
        return placeRepository.findOne(id);
    }

    @Override
    public Place findByName(String name) {
        return placeRepository.findByName(name);
    }
    
}
