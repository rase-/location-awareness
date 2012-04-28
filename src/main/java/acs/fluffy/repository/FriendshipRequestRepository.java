/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import acs.fluffy.domain.FriendshipRequest;

/**
 * DAO for FriendshipRequests. JpaRepository provides basic queries.
 * @author tonykovanen
 */
public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long> {
    
}
