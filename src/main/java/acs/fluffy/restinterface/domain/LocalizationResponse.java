/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.restinterface.domain;

import acs.fluffy.domain.Place;

/**
 *
 * @author tonykova
 */
public class LocalizationResponse {
    private boolean authenticationSuccessful;
    private String placeName;

    public boolean isAuthenticationSuccessful() {
        return authenticationSuccessful;
    }

    public void setAuthenticationSuccessful(boolean authenticationSuccessful) {
        this.authenticationSuccessful = authenticationSuccessful;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
    
    public String toString() {
        return placeName;
    }
}
