/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.spring.domain.*;
import wad.spring.repository.PlaceRepository;
import wad.spring.repository.UserRepository;

/**
 *
 * @author tonykovanen
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    PlaceRepository placeRepository;
    
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void sendOrAcceptFriendRequestByNameToById(String username, Long id) {
        User addingUser = userRepository.findByUsername(username);
        User addedUser = userRepository.findOne(id);

        if (!addingUser.getFriends().contains(addedUser)) {
            addingUser.getFriends().add(addedUser);
        }
        if (!addedUser.getFriends().contains(addingUser)) {
            addedUser.getFriends().add(addingUser);
        }

        userRepository.save(addedUser);
        userRepository.save(addingUser);
    }

    @Override
    @Transactional(readOnly = true)
    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUnaddedAndNotSelf(String username) {
        User user = userRepository.findByUsername(username);
        List<User> friends = user.getFriends();
        ArrayList<User> unadded = new ArrayList<User>();
        Role role = new Role();
        role.setRolename("user");
        for (User u : userRepository.findAll()) {
            if (!friends.contains(u) && u.getRoles().contains(role)) {
                unadded.add(u);
            }
        }
        unadded.remove(user);
        return unadded;
    }

    @Override
    @Transactional
    public void localize(String username, MeasurementForm measurementform) {
        User user = userRepository.findByUsername(username);
        ArrayList<Fingerprint> userPrints = measurementform.makeFingerprints();
        List<Place> places = placeRepository.findAll();
        double smallestError = Double.MAX_VALUE;
        Place leastErraneousPlace = places.get(0);
        for (Place p : places) {
            List<Measurement> measurements = p.getMeasurements();
            double placeErrors = 0;
            // We want to match user fingerprints for each hyperbolic measurement in each place
            // We then want to calculate the euclidean distance between these two
            for (Measurement m : measurements) {
                List<HyperbolicFingerprint> userHyperbolicPrints = makeHyperbolic(userPrints, m.getFingerprints());
                placeErrors += (euclideanDistance(userHyperbolicPrints, m.getFingerprints()));
            }
            // We take the average of all the errors for that one place and see if it is the lowest
            placeErrors = placeErrors / (measurements.size());
            if (placeErrors < smallestError) {
                smallestError = placeErrors;
                leastErraneousPlace = p;
            }
        }
        
        while (user.getHistory().size() >= 10) user.getHistory().remove(0);
        HistoryOccurrence occurrence = new HistoryOccurrence();
        occurrence.setPlace(leastErraneousPlace);
        occurrence.setMeasureTime(new Date());
        user.getHistory().add(occurrence);
        userRepository.save(user);
    }
    
    private double euclideanDistance(List<HyperbolicFingerprint> userPrints, List<HyperbolicFingerprint> reference) {
        double sum = 0;
        for (int i = 0; i < userPrints.size(); i++) {
            sum += Math.pow(userPrints.get(i).getLogarithmicRatio() - reference.get(i).getLogarithmicRatio(), 2);
        }
        return Math.sqrt(sum);
    }
    
    private List<HyperbolicFingerprint> makeHyperbolic(List<Fingerprint> userPrints, List<HyperbolicFingerprint> measurementPrints) {
        ArrayList<HyperbolicFingerprint> userHyperbolicPrints = new ArrayList<HyperbolicFingerprint>();
        HashMap<String, Double> userPrintsMap = new HashMap<String, Double>();
        for (Fingerprint print : userPrints) {
            userPrintsMap.put(print.getMacAddress(), print.getSignalStrength());
        }
        for (HyperbolicFingerprint print : measurementPrints) {
            Double firstSignalStrength = userPrintsMap.get(print.getFirstMacAddress());
            Double secondSignalStrength = userPrintsMap.get(print.getSecondMacAddress());
            // If a mac addres is not present in the user measurement
            // We want to insert a low signal strength in it's place
            if (firstSignalStrength == null) {
                firstSignalStrength = Double.valueOf(-100);
            }
            if (secondSignalStrength == null) {
                secondSignalStrength = Double.valueOf(-100);
            }
            Fingerprint firstPrint = new Fingerprint(print.getFirstMacAddress(), firstSignalStrength);
            Fingerprint secondPrint = new Fingerprint(print.getSecondMacAddress(), secondSignalStrength);
            userHyperbolicPrints.add(new HyperbolicFingerprint(firstPrint, secondPrint));
        }
        return userHyperbolicPrints;
    }
}
