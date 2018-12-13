package com.asgdrones.drones.repositories;

import com.asgdrones.drones.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.asgdrones.drones.domain.Instructor;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepoJPA extends JpaRepository<Course, Long>, CourseRepo {
    List<Course> findByInstructor(Instructor instructor);
    List<Course> findAll();
}
