package com.asgdrones.drones.repositories;

import com.asgdrones.drones.domain.Course;
import com.asgdrones.drones.domain.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepoJPA extends JpaRepository<Course,Long>, CourseRepo {
    List<Course> findByInstructor(Instructor instructor);
}
