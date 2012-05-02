package acs.fluffy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * To avoid explosion of components ;>
 * @author tonykova
 */
@Configuration
public class Config {

    @Bean
    public String overrideLog4JErrors() {
        System.setProperty("org.apache.catalina.loader.WebappClassLoader.ENABLE_CLEAR_REFERENCES", "false");
        return "done";
    }
}
