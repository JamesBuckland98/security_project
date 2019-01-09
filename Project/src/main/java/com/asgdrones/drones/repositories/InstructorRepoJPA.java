package com.asgdrones.drones.repositories;

import com.asgdrones.drones.domain.Instructor;
import com.asgdrones.drones.domain.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepoJPA extends JpaRepository<Instructor,Long>,InstructorRepo {
    @Query(value = "SELECT c.Location FROM course c JOIN instructor i ON i.InstructorID = c.Instructor_InstructorID WHERE i.login_LoginID = :loginID", nativeQuery = true)
    List<String> getInstructorAddresses(@Param("loginID") Long loginID);

    @Query(value = "SELECT LoginID FROM Login WHERE Username = :un", nativeQuery = true)
    List<Number> findByUsername(@Param("un") String un);

    @Query(value = "SELECT c.Date FROM course c JOIN instructor i ON i.InstructorID = c.Instructor_InstructorID WHERE i.login_LoginID = :loginID", nativeQuery = true)
    List<Date> getCourseDates(@Param("loginID") Long loginID);

    public Optional<Instructor> findByLogin_Id(Long id);
}
