package com.dineshwar.uniworks.repositories;

import com.dineshwar.uniworks.models.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectModel, Long> {
}
