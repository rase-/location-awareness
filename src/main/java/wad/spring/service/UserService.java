/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import java.util.List;
import wad.spring.domain.FriendshipRequest;
import wad.spring.domain.MeasurementForm;
import wad.spring.domain.User;

/**
 *
 * @author tonykovanen
 */
public interface UserService {
    User findByUsername(String username);
    User findOne(Long id);
    List<User> findAll();
    void save(User user);
    void sendOrAcceptFriendRequestByNameToById(String username, Long id);
    List<User> getUnaddedAndNotSelf(String username);
    void localize(String username, MeasurementForm measurementform);
    List<FriendshipRequest> getFriendshipRequests(String username);
}
