package com.dineshwar.uniworks.controllers;

import com.dineshwar.uniworks.models.ProjectModel;
import com.dineshwar.uniworks.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public ResponseEntity<List<ProjectModel>> getAllProjects() {
        List<ProjectModel> projects = projectRepository.findAll();
        System.out.println(projects);
        return ResponseEntity.ok(projects);
    }
}
