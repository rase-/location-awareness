/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.spring.domain.FriendshipRequest;

/**
 * DAO for FriendshipRequests. JpaRepository provides basic queries.
 * @author tonykovanen
 */
public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long> {
    
}
