-- MySQL Script generated by MySQL Workbench
-- 11/16/18 14:05:02
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema asg
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `asg` ;

-- -----------------------------------------------------
-- Schema asg
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `asg` DEFAULT CHARACTER SET latin1 ;
USE `asg` ;

-- -----------------------------------------------------
-- Table `asg`.`login`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`login` ;

CREATE TABLE IF NOT EXISTS `asg`.`login` (
  `LoginID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(20) NOT NULL,
  `Password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`LoginID`),
  UNIQUE INDEX `LoginID_UNIQUE` (`LoginID` ASC),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `asg`.`drone`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`drone` ;

CREATE TABLE IF NOT EXISTS `asg`.`drone` (
  `DroneID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Manufacturer` VARCHAR(45) NOT NULL,
  `Model` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`DroneID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `asg`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`address` ;

CREATE TABLE IF NOT EXISTS `asg`.`address` (
  `AddressID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Postcode` VARCHAR(7) NOT NULL,
  `City` VARCHAR(80) NULL,
  `Street` VARCHAR(150) NOT NULL,
  `HouseNumber` INT NULL,
  `HouseName` VARCHAR(100) NULL,
  PRIMARY KEY (`AddressID`),
  UNIQUE INDEX `AddressID_UNIQUE` (`AddressID` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `asg`.`instructor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`instructor` ;

CREATE TABLE IF NOT EXISTS `asg`.`instructor` (
  `InstructorID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(45) NOT NULL,
  `LastName` VARCHAR(45) NOT NULL,
  `PhoneNumber` CHAR(11) NOT NULL,
  `Login_LoginID` INT UNSIGNED NOT NULL,
  `address_AddressID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`InstructorID`),
  UNIQUE INDEX `InstructorID_UNIQUE` (`InstructorID` ASC),
  INDEX `fk_Instructor_Login1_idx` (`Login_LoginID` ASC),
  INDEX `fk_Instructor_address1_idx` (`address_AddressID` ASC),
  CONSTRAINT `fk_Instructor_Login1`
    FOREIGN KEY (`Login_LoginID`)
    REFERENCES `asg`.`login` (`LoginID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Instructor_address1`
    FOREIGN KEY (`address_AddressID`)
    REFERENCES `asg`.`address` (`AddressID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `asg`.`course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`course` ;

CREATE TABLE IF NOT EXISTS `asg`.`course` (
  `CourseID` INT UNSIGNED NOT NULL,
  `CourseName` VARCHAR(45) NOT NULL,
  `CourseType` VARCHAR(45) NOT NULL,
  `Instructor_InstructorID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`CourseID`),
  UNIQUE INDEX `CourseID_UNIQUE` (`CourseID` ASC),
  INDEX `fk_course_Instructor1_idx` (`Instructor_InstructorID` ASC),
  CONSTRAINT `fk_course_Instructor1`
    FOREIGN KEY (`Instructor_InstructorID`)
    REFERENCES `asg`.`instructor` (`InstructorID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `asg`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`customer` ;

CREATE TABLE IF NOT EXISTS `asg`.`customer` (
  `CandidateReferenceID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(45) NOT NULL,
  `LastName` VARCHAR(45) NOT NULL,
  `Dob` DATE NOT NULL,
  `Email` VARCHAR(128) NOT NULL,
  `PhoneNumber` CHAR(11) NOT NULL,
  `Paid` TINYINT(1) NOT NULL,
  `HoursOfFlying` INT NOT NULL,
  `Disability` TEXT(120) NULL,
  `EnglishSpeakingLevel` FLOAT NULL,
  `PreferredGSLocation` TEXT NOT NULL,
  `Insured` TINYINT(1) NOT NULL,
  `Login_LoginID` INT UNSIGNED NOT NULL,
  `drone_DroneID` INT UNSIGNED NOT NULL,
  `address_AddressID` INT UNSIGNED NOT NULL,
  `course_CourseID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`CandidateReferenceID`),
  UNIQUE INDEX `CandidateReferenceID_UNIQUE` (`CandidateReferenceID` ASC),
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC),
  INDEX `fk_customer_Login_idx` (`Login_LoginID` ASC),
  INDEX `fk_customer_drone1_idx` (`drone_DroneID` ASC),
  INDEX `fk_customer_address1_idx` (`address_AddressID` ASC),
  INDEX `fk_customer_course1_idx` (`course_CourseID` ASC),
  CONSTRAINT `fk_customer_Login`
    FOREIGN KEY (`Login_LoginID`)
    REFERENCES `asg`.`login` (`LoginID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_customer_drone1`
    FOREIGN KEY (`drone_DroneID`)
    REFERENCES `asg`.`drone` (`DroneID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_customer_address1`
    FOREIGN KEY (`address_AddressID`)
    REFERENCES `asg`.`address` (`AddressID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_customer_course1`
    FOREIGN KEY (`course_CourseID`)
    REFERENCES `asg`.`course` (`CourseID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `asg`.`results`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`results` ;

CREATE TABLE IF NOT EXISTS `asg`.`results` (
  `ResultID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Mark` INT NOT NULL,
  `PassFail` TINYINT(1) NOT NULL,
  `TypeOfTest` TEXT NOT NULL,
  `customer_CandidateReferenceID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ResultID`),
  UNIQUE INDEX `ResultID_UNIQUE` (`ResultID` ASC),
  INDEX `fk_Results_customer1_idx` (`customer_CandidateReferenceID` ASC),
  CONSTRAINT `fk_Results_customer1`
    FOREIGN KEY (`customer_CandidateReferenceID`)
    REFERENCES `asg`.`customer` (`CandidateReferenceID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `asg`.`administrator`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`administrator` ;

CREATE TABLE IF NOT EXISTS `asg`.`administrator` (
  `AdminID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(45) NOT NULL,
  `LastName` VARCHAR(45) NOT NULL,
  `Login_LoginID` INT UNSIGNED NOT NULL,
  `address_AddressID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`AdminID`),
  UNIQUE INDEX `AdminID_UNIQUE` (`AdminID` ASC),
  INDEX `fk_administrator_Login1_idx` (`Login_LoginID` ASC),
  INDEX `fk_administrator_address1_idx` (`address_AddressID` ASC),
  CONSTRAINT `fk_administrator_Login1`
    FOREIGN KEY (`Login_LoginID`)
    REFERENCES `asg`.`login` (`LoginID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_administrator_address1`
    FOREIGN KEY (`address_AddressID`)
    REFERENCES `asg`.`address` (`AddressID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `asg`.`qualification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`qualification` ;

CREATE TABLE IF NOT EXISTS `asg`.`qualification` (
  `QualificationID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `QualificationName` VARCHAR(45) NOT NULL,
  `Instructor_InstructorID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`QualificationID`),
  INDEX `fk_qualification_Instructor1_idx` (`Instructor_InstructorID` ASC),
  CONSTRAINT `fk_qualification_Instructor1`
    FOREIGN KEY (`Instructor_InstructorID`)
    REFERENCES `asg`.`instructor` (`InstructorID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
