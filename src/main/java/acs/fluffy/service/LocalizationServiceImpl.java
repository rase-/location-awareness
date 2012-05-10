/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.service;

import acs.fluffy.service.localization.Localization;
import acs.fluffy.service.localization.ByErrorAverage;
import acs.fluffy.service.localization.ByBestError;
import acs.fluffy.domain.Fingerprint;
import acs.fluffy.domain.User;
import acs.fluffy.domain.HistoryOccurrence;
import acs.fluffy.domain.Place;
import acs.fluffy.form.LocalizationType;
import acs.fluffy.form.MeasurementForm;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import acs.fluffy.repository.PlaceRepository;
import acs.fluffy.repository.UserRepository;
import acs.fluffy.restinterface.domain.LocalizationResponse;
import acs.fluffy.restinterface.domain.MeasurementContainer;

/**
 *
 * @author tonykovanen
 */
/**
 * Implementation of the localization service. 
 * @author tonykovanen
 */
@Service
public class LocalizationServiceImpl implements LocalizationService {
    private EnumMap<LocalizationType, Localization> localizationAlgorithms = new EnumMap<LocalizationType, Localization>(LocalizationType.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaceRepository placeRepository;
    
    public LocalizationServiceImpl() {
        localizationAlgorithms.put(LocalizationType.ByBestError, new ByBestError());
        localizationAlgorithms.put(LocalizationType.ByErrorAverage, new ByErrorAverage());
    }
    
    /**
     * Localizes user by selected localization measure by selecting a proper localization command object and adding closest place to user's history
     * @param username Username of user to be localized
     * @param measurementform Measurement form containing user's Fingerprint data and selected localization measure
     */
    @Override
    @Transactional
    public void localize(String username, MeasurementForm measurementform) {
        User user = userRepository.findByUsername(username);
        ArrayList<Fingerprint> userPrints = measurementform.makeFingerprints();
        // We sort the prints to make matching easier
        Collections.sort(userPrints);
        List<Place> places = placeRepository.findAll();

        //If there are no places we won't do anything
        if (places.isEmpty()) {
            return;
        }

        Place leastErraneousPlace = localizationAlgorithms.get(measurementform.getType()).localize(places, userPrints);

        cutDownHistoryOccurrencesTo9(user.getHistory());
        
        saveAsHistoryOccurrence(leastErraneousPlace, user);
    }
    

    private void cutDownHistoryOccurrencesTo9(List<HistoryOccurrence> occurrences) {
        while (occurrences.size() >= 10) {
            occurrences.remove(0);
        }
    }
    
    private void saveAsHistoryOccurrence(Place place, User user) {
        HistoryOccurrence occurrence = new HistoryOccurrence();
        occurrence.setPlace(place);
        occurrence.setMeasureTime(new Date());
        user.getHistory().add(occurrence);
        userRepository.save(user);
    }
    
    @Override
    @Transactional
    public LocalizationResponse localize(MeasurementContainer container) {
        User user = userRepository.findByUsername(container.getUsername());
        List<Fingerprint> userPrints = container.getFingerprints();
        // We sort the prints to make matching easier
        Collections.sort(userPrints);
        List<Place> places = placeRepository.findAll();

        //If there are no places we won't do anything
        if (places.isEmpty()) {
            return new LocalizationResponse();
        }

        Place leastErraneousPlace = localizationAlgorithms.get(container.getType()).localize(places, userPrints);

        cutDownHistoryOccurrencesTo9(user.getHistory());
        
        saveAsHistoryOccurrence(leastErraneousPlace, user);
        
        LocalizationResponse response = new LocalizationResponse();
        response.setPlaceName(leastErraneousPlace.getName());
        response.setAuthenticationSuccessful(true);
        return response;
    }
}
