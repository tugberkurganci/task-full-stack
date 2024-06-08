package com.tobeto_case.tobeto_case.repositories;

import com.tobeto_case.tobeto_case.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    @Query("SELECT p FROM Project p WHERE p.projectName LIKE %:projectName% ORDER BY " +
            "CASE WHEN :sortOrder = 'asc' THEN p.startDate END ASC, " +
            "CASE WHEN :sortOrder = 'desc' THEN p.startDate END DESC")
    Page<Project> findByProjectNameContainingOrderByStartDate(@Param("projectName") String projectName, @Param("sortOrder") String sortOrder, Pageable pageable);
}
