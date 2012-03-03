package wad.spring.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wad.spring.domain.Project;
import wad.spring.domain.User;
import wad.spring.repository.ProjectRepository;
import wad.spring.repository.UserRepository;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    ProjectRepository projectRepo;
    
    @Autowired
    UserRepository userRepo;
    
    @RequestMapping(value = "/home")
    public String home(Model model) {
        List<Project> projects = projectRepo.findAll();
        model.addAttribute("projects", projects);
        return "student/home";
    }
    @RequestMapping(value = "/likes/{projectId}")
    public String likeProject(@PathVariable Long projectId, Principal principal) {
        Project project = projectRepo.findOne(projectId);
        for (User u : project.getLikingStudents()) {
            if (u.getUsername().equals(principal.getName())) {
                return "redirect:/student/home";
            }
        }
        project.getLikingStudents().add(userRepo.findByUsername(principal.getName()));
        projectRepo.save(project);
        return "redirect:/student/home";
    }
    @RequestMapping(value = "/{projectId}")
    public String showProjectDetails(@PathVariable Long projectId, Model model) {
        Project project = projectRepo.findOne(projectId);
        model.addAttribute("project", project);
        return "student/project";
    }
}
