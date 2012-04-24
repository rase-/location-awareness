/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import wad.spring.domain.User;

/**
 *
 * @author tonykovanen
 */
public class UserForm {
    @Pattern(regexp="[^<>$%]+", message="Name should not contain characters <>$%")
    private String name;
    @Pattern(regexp="[a-zA-Z0-9]+", message="Username should contain only letters a-z, A-Z or 0-9")
    private String username;
    @Pattern(regexp="[^<>$%]+", message="Password should not contain characters <>$%")
    @Size(min = 4, max = 10, message = "Password should contain from 4 to 10 characters")
    private String password;
    @Pattern(regexp="[^<>$%]+", message="Password should not contain characters <>$%")
    @Size(min = 4, max = 10, message = "Password should contain from 4 to 10 characters")
    private String confirmation;

    public User makeUser() {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
    
    public String getConfirmation() {
        return confirmation;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
