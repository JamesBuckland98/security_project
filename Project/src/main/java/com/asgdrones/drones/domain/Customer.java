package com.asgdrones.drones.domain;

import io.micrometer.core.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidatereferenceID")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dob")
    private Date Dob;

    @Column(name = "email")
    private String email;

    @Size(min = 11, max = 11)
    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "hoursofflying")
    private Float hoursOfFlying;

    @Column(name = "disability")
    private String disability;

    @Column(name = "englishspeakinglevel")
    private Float englishSpeakingLevel;

    @Column(name = "preferredGSLocation")
    private String preferredGSLocation;

    @Column(name = "insured")
    private Boolean insured;

    @Column(name = "verified")
    private Boolean verified;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "login_LoginID")
    private Login login;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "drone_droneid")
    private Drone drone;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_AddressID")
    private Address address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_CourseID")
    private Course course;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn( name = "creation_creationid")
    private Creation creation;
}
