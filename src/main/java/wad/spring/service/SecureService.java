package wad.spring.service;

import org.springframework.security.access.prepost.PreAuthorize;
/**
 * A service not in hard use yet, but makes it simple to authenticate individual Controller methods
 * @author tonykovanen
 */
public interface SecureService {
    /**
     * Requires the user to be a lecturer
     */
    @PreAuthorize("hasRole('lecturer')")
    public void executeOnlyIfAuthenticatedAsLecturer();
    /**
     * Requires the user to be authenticated
     */
    @PreAuthorize("isAuthenticated()")
    public void executeOnlyIfAuthenticated();
    /**
     * Requires no authentication
     */
    public void executeFreely();
}
