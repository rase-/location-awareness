/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author tonykovanen
 */
public class ProjectForm {
    @Pattern(regexp="^[a-zA-Z0-9äöüÄÖÜ]*$", message="Name of the project should not contain any special characters")
    private String name;
    @Pattern(regexp="^[^<>%$]*$", message="The description cannot include <, >, % or $ characters")
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
