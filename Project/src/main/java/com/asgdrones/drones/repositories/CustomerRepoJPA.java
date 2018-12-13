package com.asgdrones.drones.repositories;

import com.asgdrones.drones.domain.Course;
import com.asgdrones.drones.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepoJPA extends JpaRepository<Customer, Long>, CustomerRepo {
    @Query(value = "SELECT c FROM Customer c WHERE c.firstName LIKE %:searchQuery% " +
            "or c.lastName LIKE %:searchQuery% " +
            "or c.preferredGSLocation LIKE %:searchQuery%")
    public List<Customer> findBySearchTerm(@Param("searchQuery") String searchQuery);
    public Customer findByLogin_Id(Long LoginID);
    List<Customer> findAllById(Long id);
    List<Customer> findByCourseId(Long id);
    List<Course> findByCourse(Long id);
}

