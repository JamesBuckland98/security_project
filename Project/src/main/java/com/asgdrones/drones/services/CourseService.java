package com.asgdrones.drones.services;

import com.asgdrones.drones.domain.Address;
import com.asgdrones.drones.domain.Course;
import com.asgdrones.drones.domain.Login;
import com.asgdrones.drones.repositories.CourseRepoJPA;
import com.asgdrones.drones.domain.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class CourseService implements CourseServiceInterface {
    private CourseRepoJPA courseRepoJPA;

    @Autowired
    CourseService(CourseRepoJPA cRepo) {
        courseRepoJPA = cRepo;
    }

    @Override
    public List<Course> getCourses() {
        List<Course> courseList = courseRepoJPA.findAll();
        return courseList;
    }

    @Override
    public List<Course> findByInstructor(Instructor instructor) {
        return courseRepoJPA.findByInstructor(instructor);

    }

    @Override
    public void addCourse(Course course) {
        courseRepoJPA.save(course);
    }
}
