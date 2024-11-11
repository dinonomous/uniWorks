package com.dineshwar.uniworks.repositories;

import com.dineshwar.uniworks.models.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectModel, Long> {

    // Find projects by clientId
    List<ProjectModel> findByClientId(Long clientId);
}
