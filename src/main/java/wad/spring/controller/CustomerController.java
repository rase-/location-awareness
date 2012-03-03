package wad.spring.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wad.spring.domain.Project;
import wad.spring.domain.ProjectForm;
import wad.spring.domain.User;
import wad.spring.repository.ProjectRepository;
import wad.spring.repository.UserRepository;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    ProjectRepository projectRepo;
    
    @Autowired
    UserRepository userRepo;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model, Principal principal) {
        List<Project> allProjects = projectRepo.findAll();
        List<Project> myProjects = new ArrayList<Project>();
        model.addAttribute("projectform", new ProjectForm());
        for(Project p : allProjects) {
            if (p.getCustomer().getUsername().equals(principal.getName())) {
                myProjects.add(p);
            }
        }
        model.addAttribute("projects", myProjects);
        return "customer/home";
    }
    
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String addProject(@Valid @ModelAttribute("projectform") ProjectForm projectform, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "customer/home";
        }
        Project project = new Project();
        project.setCustomer(userRepo.findByUsername(principal.getName()));
        project.setLikingStudents(new ArrayList<User>());
        project.setDescription(projectform.getDescription());
        project.setName(projectform.getName());
        projectRepo.save(project);
        return "redirect:/customer/home";
    }
    
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public String showProject(@PathVariable Long projectId, Model model) {
        Project project = projectRepo.findOne(projectId);
        model.addAttribute("project", project);
        model.addAttribute("projectform", new ProjectForm());
        return "customer/project";
    }
    @RequestMapping(value = "/{projectId}", method = RequestMethod.POST)
    public String changeDescription(@Valid @ModelAttribute("projectform") ProjectForm projectform, BindingResult result, @PathVariable Long projectId) {
        if (result.hasErrors()) {
            return "customer/project";
        }
        Project project = projectRepo.findOne(projectId);
        project.setName(projectform.getName());
        project.setDescription(projectform.getDescription());
        projectRepo.save(project);
        return "redirect:/customer/" + projectId;
    }
}
