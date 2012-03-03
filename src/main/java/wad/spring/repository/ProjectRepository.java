/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.spring.domain.Project;

/**
 *
 * @author tonykovanen
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
}
