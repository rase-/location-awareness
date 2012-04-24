/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.request.MockMvcRequestBuilders;
import org.springframework.test.web.server.result.MockMvcResultMatchers;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import form.UserForm;

/**
 *
 * @author tonykovanen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-context.xml", 
    "file:src/main/webapp/WEB-INF/spring-database.xml"})
public class HomeControllerTest {
    private MockMvc mockMvc;
    
    public HomeControllerTest() {
    }

    
    @Before
    public void setUp() {
        String[] contextLoc = {"file:src/main/webapp/WEB-INF/spring-context.xml",
            "file:src/main/webapp/WEB-INF/spring-database.xml"};
        String warDir = "src/main/webapp";
        mockMvc = MockMvcBuilders.xmlConfigSetup(contextLoc).
                configureWebAppRootDir(warDir, false).build();
    }
    
    @Test
    public void registrationShowsCorrectViewWithCorrectModel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("registration")).
                andExpect(MockMvcResultMatchers.model().attributeExists("userForm"));
    }
    
    
}
