package com.dineshwar.uniworks.controllers;

import com.dineshwar.uniworks.models.AppUser;
import com.dineshwar.uniworks.models.ProjectModel;
import com.dineshwar.uniworks.repositories.ProjectRepository;
import com.dineshwar.uniworks.repositories.AppUserRepository; // Correct import for AppUserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "http://localhost:5173")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AppUserRepository appUserRepository; // Correct reference to AppUserRepository

    // Get all projects
    @GetMapping
    public ResponseEntity<List<ProjectModel>> getAllProjects() {
        List<ProjectModel> projects = projectRepository.findAll();
        return ResponseEntity.ok(projects);
    }

    // Create a new project
    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectModel project) {
        // Retrieve the current authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> currentUserOptional = Optional.ofNullable(appUserRepository.findByUsername(username));

        if (currentUserOptional.isEmpty()) {
            return ResponseEntity.status(404).body("User not found.");
        }

        AppUser currentUser = currentUserOptional.get();

        // Check if the user has the role "Client"
        if (!"Client".equals(currentUser.getRole())) {
            return ResponseEntity.status(403).body("Only clients can create a project.");
        }

        // Set the clientId to the logged-in user’s id
        project.setClientId(currentUser.getId());

        // Set the created and updated timestamp
        project.setCreatedAt(new Date());
        project.setUpdatedAt(new Date());

        // Save the project
        ProjectModel savedProject = projectRepository.save(project);

        return ResponseEntity.status(201).body(savedProject);
    }
}
