package acs.fluffy.service;

import acs.fluffy.domain.User;
import acs.fluffy.domain.Role;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import acs.fluffy.repository.PlaceRepository;
import acs.fluffy.repository.UserRepository;
/**
 * Implementation only for testing an populating database with testusers
 * @author tonykovanen
 */
@Service
public class SecureServiceImpl implements SecureService {
    @Autowired
    private PlaceRepository placeRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public void executeOnlyIfAuthenticatedAsLecturer() {
        System.out.println("The guy must be an admin! Note that this was configured in the interface.");
    }

    @Override
    public void executeOnlyIfAuthenticated() {
        System.out.println("The guy must be authenticated! Note that this was configured in the interface.");
    }

    @Override
    @Transactional
    public void executeFreely() {
        // populate db if needed
        if (userRepository.count() > 0) {
            return;
        }

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testuser");
        user.setName("testuser");
        user = userRepository.save(user);


        List<Role> roles = new ArrayList();

        Role role = new Role();
        role.setRolename("user");
        roles.add(role);
        
        user.setRoles(roles);
        
        User admin = new User();
        admin.setUsername("testadmin");
        admin.setPassword("testadmin");
        admin.setName("testadmin");
        admin = userRepository.save(admin);


        roles = new ArrayList();

        role = new Role();
        role.setRolename("admin");
        roles.add(role);
        
        admin.setRoles(roles);
        
        User tony = new User();
        tony.setUsername("tony");
        tony.setPassword("lol");
        tony.setName("Tony Kovanen");
        tony = userRepository.save(tony);
        
        roles = new ArrayList();
        role = new Role();
        role.setRolename("user");
        roles.add(role);
        role = new Role();
        role.setRolename("admin");
        roles.add(role);
        
        tony.setRoles(roles);
        
    }
}
