package com.asgdrones.drones.services;

import com.asgdrones.drones.domain.Instructor;
import com.asgdrones.drones.domain.Login;
import com.asgdrones.drones.repositories.CourseRepoJPA;
import com.asgdrones.drones.repositories.InstructorRepoJPA;
import com.asgdrones.drones.repositories.LoginRepoJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorService implements InstructorServiceInterface {
    private InstructorRepoJPA instructorRepoJPA;
    private CourseRepoJPA courseRepoJPA;
    private LoginRepoJPA loginRepoJPA;

    @Autowired
    InstructorService(InstructorRepoJPA iRepo, CourseRepoJPA cRepo, LoginRepoJPA aLoginRepo) {
        instructorRepoJPA = iRepo;
        courseRepoJPA = cRepo;
        loginRepoJPA=aLoginRepo;
    }

    @Override
    @Cacheable("InstructorAddress")
    public List<String> getInstructorAddress(Long loginID) {
        List<String> addresses = instructorRepoJPA.getInstructorAddresses(loginID);
        return addresses;
    }

    @Override
    @Cacheable("InstructorId")
    public Instructor getInstructorById(Long id) {
        Optional<Login> theLogin = loginRepoJPA.findById(id);
        Optional<Instructor> theInstructor = instructorRepoJPA.findByLogin_Id(theLogin.get().getId());
        return theInstructor.get();
    }

    @Override
    @Cacheable("InstrucorCourseDate")
    public List<Date> getCourseDates(Long loginID) {
        List<Date> dates = instructorRepoJPA.getCourseDates(loginID);
        return dates;
    }

    @Override
    @Cacheable("Instructor")
    public Instructor getInstructor(Long id){
        Optional<Instructor> instructorOptional = instructorRepoJPA.findByLogin_Id(id);
        if (instructorOptional.isPresent()) {
            Instructor instructor = instructorOptional.get();
            return instructor;
        }else {
            return null;
        }
    }
}
