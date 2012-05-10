/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.restinterface.authentication;

/**
 *
 * @author tonykova
 */
public interface AuthenticationService {
    public boolean authenticate(String username, String password);
}
