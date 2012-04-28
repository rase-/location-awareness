/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.service;

import java.util.List;
import acs.fluffy.domain.FriendshipRequest;
import acs.fluffy.form.MeasurementForm;
import acs.fluffy.domain.User;
import acs.fluffy.form.UserForm;

/**
 * Operations defined for users
 * @author tonykovanen
 */
public interface UserService {
    /**
     * Finds a user by given username
     * @param username Given username
     * @return Returns found user or null if not found
     */
    User findByUsername(String username);
    /**
     * Finds user by id
     * @param id Given id
     * @return Returns found user or null if not found
     */
    User findOne(Long id);
    /**
     * Returns all users
     * @return List of all users
     */
    List<User> findAll();
    /**
     * Saves a user object into database
     * @param user Given user
     */
    void save(User user);
    /**
     * Sends or accepts a friend request depending if the requested user has asked the requesting user before
     * @param username Username of requesting user
     * @param id Id of requested user
     */
    void sendOrAcceptFriendRequestByNameToById(String username, Long id);
    /**
     * Returns all friends that are not added as friends yet, and is not the user him/herself
     * @param username Given username
     * @return List of users not friends with or not self
     */
    List<User> getUnaddedAndNotSelf(String username);
    /**
     * Gives all received friendship requests
     * @param username Given username
     * @returnList of all friendship requests received
     */
    List<FriendshipRequest> getFriendshipRequests(String username);
    /**
     * Finds user information if first user is friends with the second
     * @param username Username of the first user
     * @param friendsId Id of the second user
     * @return Returns the requested user if friends, otherwise returns null
     */
    User findIfFriends(String username, Long friendsId);
    /**
     * Registers a new user from UserForm data
     * @param userForm Data of new user
     */
    void register(UserForm userForm);
    /**
     * Deletes selected user or the user himself
     * @param userId Id of user to be deleted
     * @param username Id of invoking user
     */
    void deleteUser(Long userId, String username);
    /**
     * Promote user as admin
     * @param userId Id of user to be promoted
     */
    void promoteAdmin(Long userId);
    
}
