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


    /**
     *
     * Goes through each place: matches user's fingerprints with each
     * measurement in the place (missing values are given a -100 signal strength
     * which is really low) as hyperbolic fingerprints and a squared error is
     * calculated using euclidean distance. Error of a measurement in a place is
     * calculated and matched against the least erraneous place. The best is
     * then updated to the least erraneous place. In the end the best place is
     * added to history of the user.
     *
     * @param username Given username
     * @param measurementform Given measurementinformation
     */
    

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
