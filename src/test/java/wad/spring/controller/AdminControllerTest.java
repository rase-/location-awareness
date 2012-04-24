/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.controller;

import org.junit.*;
import static org.junit.Assert.*;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.request.MockMvcRequestBuilders;
import org.springframework.test.web.server.result.MockMvcResultMatchers;
import org.springframework.test.web.server.setup.MockMvcBuilders;

/**
 *
 * @author tonykovanen
 */
public class AdminControllerTest {
    private MockMvc mockMvc;
    

    
    @Before
    public void setUp() {
        String[] contextLoc = {"file:src/main/webapp/WEB-INF/spring-context.xml",
            "file:src/main/webapp/WEB-INF/spring-database.xml"};
        String warDir = "src/main/webapp";
        mockMvc = MockMvcBuilders.xmlConfigSetup(contextLoc).
                configureWebAppRootDir(warDir, false).build();
    }

    
    @Test
    public void showPlacesReturnsPlacesViewAndHasModelAttributesPlaceForFormAndPlacesToShow() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/places")).
                andExpect(MockMvcResultMatchers.view().name("admin/places")).
                andExpect(MockMvcResultMatchers.model().attributeExists("place")).
                andExpect(MockMvcResultMatchers.model().attributeExists("places"));
    }
    
}