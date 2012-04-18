/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.spring.domain.*;
import wad.spring.repository.FriendshipRequestRepository;
import wad.spring.repository.PlaceRepository;
import wad.spring.repository.UserRepository;

/**
 * An implementation of the UserService using PlaceRepository, UserRepository
 * and FriendshipRequestRepository.
 *
 * @author tonykovanen
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendshipRequestRepository friendshipRequestRepository;

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

    /**
     * Finds both users and checks if the requested user has requested the
     * requesting user as a friend. If so, then deletes friendrequests from both
     * users (from the other user) and adds them as eachothers' friends.
     * Otherwise a friendship request is sent to the requested user from
     * requesting user. In the end both users' information is saved
     *
     * @param username Username of requesting user
     * @param id Id of requested user
     */
    @Override
    @Transactional
    public void sendOrAcceptFriendRequestByNameToById(String username, Long id) {
        User addingUser = userRepository.findByUsername(username);
        User addedUser = userRepository.findOne(id);

//        if (!addingUser.getFriends().contains(addedUser)) {
//            addingUser.getFriends().add(addedUser);
//        }
//        if (!addedUser.getFriends().contains(addingUser)) {
//            addedUser.getFriends().add(addingUser);
//        }

        for (FriendshipRequest f : addingUser.getReceivedFriendRequests()) {
            if (f.getSender().equals(addedUser)) {
                addingUser.getReceivedFriendRequests().remove(f);
                friendshipRequestRepository.delete(f);
                addingUser.getFriends().add(addedUser);
                addedUser.getFriends().add(addingUser);
                userRepository.save(addedUser);
                userRepository.save(addingUser);
                return;
            }
        }

        for (FriendshipRequest f : addedUser.getReceivedFriendRequests()) {
            if (f.getSender().equals(addingUser)) {
                return;
            }
        }

        FriendshipRequest request = new FriendshipRequest();
        request.setSender(addingUser);
        addedUser.getReceivedFriendRequests().add(request);

        userRepository.save(addedUser);
        userRepository.save(addingUser);
    }

    @Override
    @Transactional(readOnly = true)
    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    /**
     * Goes through all users and checks if they are in given users friendlist.
     * If not they are added to a list. In the end the list is returned after
     * the user himself is removed from the list.
     *
     * @param username Given username
     * @return A list of unadded friends and not self
     */
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

//    /**
//     * Goes through each place: matches user's fingerprints with each measurement in the place (missing values are given a -100 signal strength which is really low) as hyperbolic fingerprints and a squared error is calculated using euclidean distance. The average of errors from all measruements in a place is calculated and matched against the least erraneous place. The best is then updated to the least erraneous place. In the end the best place is added to history of the user.
//     * @param username Given username
//     * @param measurementform Given measurementinformation
//     */
//    @Override
//    @Transactional
//    public void localize(String username, MeasurementForm measurementform) {
//        User user = userRepository.findByUsername(username);
//        ArrayList<Fingerprint> userPrints = measurementform.makeFingerprints();
//        List<Place> places = placeRepository.findAll();
//        
//        //If there are no places we won't do anything
//        if (places.isEmpty()) {
//            return;
//        }
//        
//        double smallestError = Double.MAX_VALUE;
//        Place leastErraneousPlace = places.get(0);
//        for (Place p : places) {
//            List<Measurement> measurements = p.getMeasurements();
//            double placeErrors = 0;
//            // We want to match user fingerprints for each hyperbolic measurement in each place
//            // We then want to calculate the euclidean distance between these two
//            for (Measurement m : measurements) {
//                List<HyperbolicFingerprint> userHyperbolicPrints = makeHyperbolic(userPrints, m.getFingerprints());
//                placeErrors += Math.abs((euclideanDistance(userHyperbolicPrints, m.getFingerprints())));
//            }
//            // We take the average of all the errors for that one place and see if it is the lowest
//            placeErrors = placeErrors / (measurements.size());
//            if (placeErrors < smallestError) {
//                smallestError = placeErrors;
//                leastErraneousPlace = p;
//            }
//        }
//        
//        while (user.getHistory().size() >= 10) user.getHistory().remove(0);
//        HistoryOccurrence occurrence = new HistoryOccurrence();
//        occurrence.setPlace(leastErraneousPlace);
//        occurrence.setMeasureTime(new Date());
//        user.getHistory().add(occurrence);
//        userRepository.save(user);
//    }
    // Differs from ^ by not using average of each measurement error in a place. At first sight seems to work better.
    /**
     *
     * Goes through each place: matches user's fingerprints with each
     * measurement in the place (missing values are given a -100 signal strength
     * which is really low) as hyperbolic fingerprints and a squared error is
     * calculated using euclidean distance. Error of a measruement in a place is
     * calculated and matched against the least erraneous place. The best is
     * then updated to the least erraneous place. In the end the best place is
     * added to history of the user.
     *
     * @param username Given username
     * @param measurementform Given measurementinformation
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

        double smallestError = Double.MAX_VALUE;
        Place leastErraneousPlace = places.get(0);
        for (Place p : places) {
            List<Measurement> measurements = p.getMeasurements();
            // We want to match user fingerprints for each hyperbolic measurement in each place
            // We then want to calculate the euclidean distance between these two
            for (Measurement m : measurements) {
                double placeError = matchPrintsAndMakeHyperbolicAndCalculateError(userPrints, m.getFingerprints());
                if (placeError < smallestError) {
                    smallestError = placeError;
                    leastErraneousPlace = p;
                }
            }

        }

        while (user.getHistory().size() >= 10) {
            user.getHistory().remove(0);
        }
        HistoryOccurrence occurrence = new HistoryOccurrence();
        occurrence.setPlace(leastErraneousPlace);
        occurrence.setMeasureTime(new Date());
        user.getHistory().add(occurrence);
        userRepository.save(user);
    }

    /**
     * Calculates euclidean distance between user's hyperbolic prints and one
     * reference measurement
     *
     * @param userPrints User's prints
     * @param reference One measurement in a place
     * @return Squared error
     */
    private double euclideanDistance(List<HyperbolicFingerprint> userPrints, List<HyperbolicFingerprint> reference) {
        double sum = 0;
        for (int i = 0; i < userPrints.size(); i++) {
            sum += Math.pow(userPrints.get(i).getLogarithmicRatio() - reference.get(i).getLogarithmicRatio(), 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * Matches user's regular fingerprints with given measurement and makes them
     * hyperbolic prints
     *
     * @param userPrints Users regular prints
     * @param measurementPrints Hyperbolic prints of an individual measurement
     * @return Hyperic userprints that match mac addresses of reference prints
     */
    private List<HyperbolicFingerprint> matchHyperbolic(List<Fingerprint> userPrints, List<HyperbolicFingerprint> measurementPrints) {
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

    /**
     * Makes a hyperbolic fingerprint of each unique pair of prints
     *
     * @param regular Regular fingerprints as list
     * @return Hyperbolic fingerprints as list
     */
    private List<HyperbolicFingerprint> makeHyperbolic(List<Fingerprint> regular) {
        ArrayList<HyperbolicFingerprint> hyperbolic = new ArrayList<HyperbolicFingerprint>();
        for (int i = 1; i < regular.size(); i++) {
            for (int j = 0; j < i; j++) {
                hyperbolic.add(new HyperbolicFingerprint(regular.get(i), regular.get(j)));
            }

        }
        return hyperbolic;
    }
    /**
     * Matches prints, then makes them hyperbolic and calculates euclidean distance
     * @param userPrints Fingprints of user
     * @param databasePrints Fingerprints of one measurement
     * @return Euclidean distance between the two prints
     */
    private double matchPrintsAndMakeHyperbolicAndCalculateError(List<Fingerprint> userPrints, List<Fingerprint> databasePrints) {
        // Let's now assume sorting has taken place before
//        Collections.sort(userPrints); //We might want to position these somewhere else so we only need to sort once per localization
//        Collections.sort(databasePrints);
        int i = 0;
        int j = 0;
        ArrayList<Fingerprint> matchedUserPrints = new ArrayList<Fingerprint>();
        ArrayList<Fingerprint> matchedDatabasePrints = new ArrayList<Fingerprint>();

        while (i < userPrints.size() || j < databasePrints.size()) {
            if (i == userPrints.size()) {
                matchedUserPrints.add(new Fingerprint(databasePrints.get(j).getMacAddress(), -100));
                matchedDatabasePrints.add(databasePrints.get(j));
                j++;
            } else if (j == databasePrints.size()) {
                matchedUserPrints.add(userPrints.get(i));
                matchedDatabasePrints.add(new Fingerprint(userPrints.get(i).getMacAddress(), -100));
                i++;
            } else if (userPrints.get(i).getMacAddress().equals(databasePrints.get(j).getMacAddress())) {
                matchedUserPrints.add(userPrints.get(i));
                matchedDatabasePrints.add(databasePrints.get(j));
                i++;
                j++;
                
            } else if (userPrints.get(i).compareTo(databasePrints.get(j)) < 0) {
                matchedUserPrints.add(userPrints.get(i));
                matchedDatabasePrints.add(new Fingerprint(userPrints.get(i).getMacAddress(), -100));
                i++;
                
            } else {
                matchedUserPrints.add(new Fingerprint(databasePrints.get(j).getMacAddress(), -100));
                matchedDatabasePrints.add(databasePrints.get(j));
                j++;
            }

        }
        
        return euclideanDistance(makeHyperbolic(matchedUserPrints), makeHyperbolic(matchedDatabasePrints));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FriendshipRequest> getFriendshipRequests(String username) {
        return userRepository.findByUsername(username).getReceivedFriendRequests();
    }

    /**
     * Checks if first user's friends has the second user and returns the other
     * if yes
     *
     * @param username First user's username
     * @param friendsId Second user's id
     * @return Second user if friends, otherwise null
     */
    @Override
    @Transactional(readOnly = true)
    public User findIfFriends(String username, Long friendsId) {
        User user = userRepository.findByUsername(username);
        User possibleFriend = userRepository.findOne(friendsId);
        if (user.getFriends().contains(possibleFriend)) {
            return possibleFriend;
        }
        return null;
    }

    /**
     * Validation is done on controller level so just creates a new user and
     * saves it to database
     *
     * @param userForm Data of user in UserForm
     */
    @Transactional
    @Override
    public void register(UserForm userForm) {
        User user = userForm.makeUser();
        List<Role> roles = new ArrayList<Role>();
        Role role = new Role();
        role.setRolename("user");
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
    }
}
