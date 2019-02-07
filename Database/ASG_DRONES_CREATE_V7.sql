-- MySQL Workbench Forward Engineering
SET GLOBAL log_bin_trust_function_creators = 1;
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
-- Table `asg`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`address` ;

CREATE TABLE IF NOT EXISTS `asg`.`address` (
  `AddressID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, -- surrogate primary key in order to uniquely identify the  address entity
  `Postcode` VARCHAR(8) NOT NULL, -- range of a max of 8 due to users sometimes entering space in the postcode
  `City` VARCHAR(85) NOT NULL, -- range for city with a maximum of 85 characters due to longest city name in UK being 85 characters 
  `Street` VARCHAR(150) NOT NULL, -- range for user to enter street name. 50 is an arbitrary number to save space
  `HouseNumber` INT(11) NULL DEFAULT NULL, -- can be null due to not all houses having a house number. Can only be a whole number.
  `HouseName` VARCHAR(100) NULL DEFAULT NULL, -- can be null due to not all houses having a name. 100 is arbitrary number to save space
  PRIMARY KEY (`AddressID`),
  UNIQUE INDEX `AddressID_UNIQUE` (`AddressID` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

Insert into address (AddressID, Postcode, City, Street, HouseNumber,HouseName) values (1, 'CF24 4AN','Cardiff','Miskin street', 4, Null);
Insert into address (AddressID, Postcode, City, Street, HouseNumber,HouseName) values (2, 'DY91 5DA','DUDLEY','Grove Road', 7155, Null);
Insert into address (AddressID, Postcode, City, Street, HouseNumber,HouseName) values (3, 'M81 5OZ','MANCHESTER','Chester Road', 8, Null);
Insert into address (AddressID, Postcode, City, Street, HouseNumber,HouseName) values (4, 'TW92 4IA','TWICKENHAM','New Street', 231, Null);
Insert into address (AddressID, Postcode, City, Street, HouseNumber,HouseName) values (5, 'L39 4EG','LIVERPOOL','Alexander Road', 2, Null);
Insert into address (AddressID, Postcode, City, Street, HouseNumber,HouseName) values (6, 'DT36 9NR','DORCHESTER','Grange Road', 7, Null);
Insert into address (AddressID, Postcode, City, Street, HouseNumber,HouseName) values (7, 'SG69 7NE','STEVENAGE','Main Road', 484, Null);
Insert into address (AddressID, Postcode, City, Street, HouseNumber,HouseName) values (8, 'KT51 1KD','KINGSTON UPON THAMES','St. John’s Road', 86, Null);
Insert into address (AddressID, Postcode, City, Street, HouseNumber,HouseName) values (9, 'GL96 5YM','GLOUCESTER','Queen Street', 52, Null);
Insert into address (AddressID, Postcode, City, Street, HouseNumber,HouseName) values (10, 'ST76 9DU','STOKE-ON-TRENT','The Green', 7861, Null);

-- -----------------------------------------------------
-- Table `asg`.`login`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`login` ;

CREATE TABLE IF NOT EXISTS `asg`.`login` (
  `LoginID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, -- surrogate primary key in order to identify login credentials 
  `Username` VARCHAR(20)  NOT NULL, -- required attribute that the user can enter a range up to a max of 20 characters for their username
  `Password` VARCHAR(100) NOT NULL, -- required attribute that the user can enter a password up to 100 characters
  `Access` VARCHAR(45) NOT NULL, -- reuqired attribute that gies the user specific acess level to the webiste. This will either be customer, admin or instructor
  PRIMARY KEY (`LoginID`),
  UNIQUE INDEX `LoginID_UNIQUE` (`LoginID` ASC),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

Insert into login (LoginID, Username, Password, Access) values (1,'username', 'password','admin');
Insert into login (LoginID, Username, Password, Access) values (2,'rAVINGeR', '5fGSTv','admin');
Insert into login (LoginID, Username, Password, Access) values (3,'repherAI', 'Zm2nNz','instructor');
Insert into login (LoginID, Username, Password, Access) values (4,'bstrIUMI', 'Z9tgtq','instructor');
Insert into login (LoginID, Username, Password, Access) values (5,'ACKonUER', 'L78fdf','instructor');
Insert into login (LoginID, Username, Password, Access) values (6,'nICIomoT', '8kMDpV','customer');
Insert into login (LoginID, Username, Password, Access) values (7,'TusaNtHe', '35fFNQ','customer');
Insert into login (LoginID, Username, Password, Access) values (8,'TRySIaPu', 'fpNF3J','customer');
Insert into login (LoginID, Username, Password, Access) values (9,'EstRathE', 'e4DUdA','customer');
Insert into login (LoginID, Username, Password, Access) values (10,'PenDescu', '9taN6y','customer');


-- -----------------------------------------------------
-- Table `asg`.`administrator`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`administrator` ;

CREATE TABLE IF NOT EXISTS `asg`.`administrator` (
  `AdminID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, -- surrogate primary key for the admon entity to be uniquely identified
  `FirstName` VARCHAR(45) NOT NULL, -- required field to know the admin's first name of a range up to 45 characters. 45 is an arbitrary number to save space
  `LastName` VARCHAR(45) NOT NULL, -- required field to know the admin's last name of a range up to 45 characters. 45 is an arbitrary number to save space
  `address_AddressID` INT(10) UNSIGNED NOT NULL, -- foreign key to link admin tabke with adress table. This is unsigned inorder to store more bits
  `login_LoginID` INT(10) UNSIGNED NOT NULL, -- foreign key to link the admin table with login table. This is unsigned inorder to store more bits
  PRIMARY KEY (`AdminID`),
  UNIQUE INDEX `AdminID_UNIQUE` (`AdminID` ASC),
  INDEX `fk_administrator_address1_idx` (`address_AddressID` ASC),
  INDEX `fk_administrator_login1_idx` (`login_LoginID` ASC),
  CONSTRAINT `fk_administrator_address1`
    FOREIGN KEY (`address_AddressID`)
    REFERENCES `asg`.`address` (`AddressID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_administrator_login1`
    FOREIGN KEY (`login_LoginID`)
    REFERENCES `asg`.`login` (`LoginID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

insert into administrator (AdminID, FirstName, LastName, address_AddressID, login_LoginID) values (1,'Selin','Martin',1,1);
insert into administrator (AdminID, FirstName, LastName, address_AddressID, login_LoginID) values (2,'Billy','Mclellan',5,2);
-- -----------------------------------------------------
-- Table `asg`.`qualification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`qualification` ;

CREATE TABLE IF NOT EXISTS `asg`.`qualification` (
  `QualificationID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, -- primary key in order for the instructor to uniquley identify each qualification entitiy
  `QualificationName` VARCHAR(128) NOT NULL, -- this is an attribute with the name of the qualification with a range of up to 128 characthers. 128 is arbitary
  PRIMARY KEY (`QualificationID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

insert into qualification (QualificationID, QualificationName) values (1,'Medical Assistant');
insert into qualification (QualificationID, QualificationName) values (2,'Court Reporter');
insert into qualification (QualificationID, QualificationName) values (3,'Public Relations Specialist');

-- -----------------------------------------------------
-- Table `asg`.`instructor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`instructor` ;

CREATE TABLE IF NOT EXISTS `asg`.`instructor` (
  `InstructorID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, -- surrogate primary key to uniquley identify the instuctor entities in the table
  `FirstName` VARCHAR(45) NOT NULL, -- required field to know the admin's first name of a range up to 45 characters. 45 is an arbitrary number to save space 
  `LastName` VARCHAR(45) NOT NULL, -- required field to know the admin's last name of a range up to 45 characters. 45 is an arbitrary number to save space
  `PhoneNumber` CHAR(11) NOT NULL, -- required field. has to be exactly 11 digits due to UK phone nuber standards having 11 digits 
  `address_AddressID` INT(10) UNSIGNED NOT NULL, -- foreign key links the address and the instructor table. This is required and not null.
  `qualification_QualificationID` INT(10) UNSIGNED NULL DEFAULT NULL,-- foreign key that links the instructor account to the qualification. Optional so can be null
  `login_LoginID` INT(10) UNSIGNED NOT NULL, -- foreign key that links the login table to the instructor table to get login credentials.
  PRIMARY KEY (`InstructorID`),
  UNIQUE INDEX `InstructorID_UNIQUE` (`InstructorID` ASC),
  INDEX `fk_Instructor_address1_idx` (`address_AddressID` ASC),
  INDEX `fk_instructor_qualification1_idx` (`qualification_QualificationID` ASC),
  INDEX `fk_instructor_login1_idx` (`login_LoginID` ASC),
  CONSTRAINT `fk_Instructor_address1`
    FOREIGN KEY (`address_AddressID`)
    REFERENCES `asg`.`address` (`AddressID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_instructor_qualification1`
    FOREIGN KEY (`qualification_QualificationID`)
    REFERENCES `asg`.`qualification` (`QualificationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_instructor_login1`
    FOREIGN KEY (`login_LoginID`)
    REFERENCES `asg`.`login` (`LoginID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

Insert into instructor(InstructorID,FirstName,LastName,PhoneNumber,address_AddressID,qualification_QualificationID,login_LoginID) values (1,'Isabella', 'Hendrix', '03069990663',2,1,3);
Insert into instructor(InstructorID,FirstName,LastName,PhoneNumber,address_AddressID,qualification_QualificationID,login_LoginID) values (2,'Christopher', 'Squires', '03069990627',6,2,4);
Insert into instructor(InstructorID,FirstName,LastName,PhoneNumber,address_AddressID,qualification_QualificationID,login_LoginID) values (3,'Samina', 'Beasley', '03069990546',3,3,5);

-- -----------------------------------------------------
-- Table `asg`.`course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`course` ;

CREATE TABLE IF NOT EXISTS `asg`.`course` (
  `CourseID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, -- surrogate primary key that uniquely identifies the course entity in the table 
  `Name` VARCHAR(45) NOT NULL, -- require field that can be a range up to 45 characters. Therefore cannot be null
  `Type` VARCHAR(45) NULL, -- require field that can be a range up to 45 characters. Therefore cannot be null
  `Location` VARCHAR(85) NOT NULL, -- location of the course that is required. This is required for people to know where to go and cannot be null
  `Date` DATE NOT NULL, -- Date of the course. This is required for people to know what day to attend the course so cannot be null
  `Instructor_InstructorID` INT(10) UNSIGNED NULL DEFAULT NULL, -- foreign key to link course to an instructor. This can be null if an instructor is not avaliable
  PRIMARY KEY (`CourseID`),
  UNIQUE INDEX `CourseID_UNIQUE` (`CourseID` ASC),
  INDEX `fk_course_Instructor1_idx` (`Instructor_InstructorID` ASC),
  CONSTRAINT `fk_course_Instructor1`
    FOREIGN KEY (`Instructor_InstructorID`)
    REFERENCES `asg`.`instructor` (`InstructorID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

Insert into course (CourseID, Name, Type, Location, Date, Instructor_InstructorID) values (1, 'course1', 'type1', 'Cardiff', '2018-12-31',1);
Insert into course (CourseID, Name, Type, Location, Date, Instructor_InstructorID) values (2, 'course2', 'type2', 'Bristol', '2018-12-31',1);
Insert into course (CourseID, Name, Type, Location, Date, Instructor_InstructorID) values (3, 'course3', 'type3', 'Nottingham', '2018-12-31',2);
Insert into course (CourseID, Name, Type, Location, Date, Instructor_InstructorID) values (4, 'course4', 'type1', 'Sheffield', '2018-12-31',3);
Insert into course (CourseID, Name, Type, Location, Date, Instructor_InstructorID) values (5, 'course5', 'type2', 'Swansea', '2018-12-31',3);
Insert into course (CourseID, Name, Type, Location, Date, Instructor_InstructorID) values (6, 'course6', 'type3', 'Leeds', '2018-12-31',2);
Insert into course (CourseID, Name, Type, Location, Date, Instructor_InstructorID) values (7, 'course7', 'type1', 'London', '2018-12-31',1);
Insert into course (CourseID, Name, Type, Location, Date, Instructor_InstructorID) values (8, 'course8', 'type2', 'Worcester', '2018-12-31',1);
Insert into course (CourseID, Name, Type, Location, Date, Instructor_InstructorID) values (9, 'course9', 'type3', 'Aberdeen', '2018-12-31',3);
Insert into course (CourseID, Name, Type, Location, Date, Instructor_InstructorID) values (10,'course10', 'type1', 'Plymouth', '2018-12-31',3);

-- -----------------------------------------------------
-- Table `asg`.`creation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`creation` ;

CREATE TABLE IF NOT EXISTS `asg`.`creation` (
  `CreationID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, -- surrogate primary key that uniquely identifies the creation entity in the table 
  `CreationDate` DATETIME NULL DEFAULT NULL, -- creation date is a date time of when the customer was created in the database and is required by data protection
  `DeletionDate` DATETIME NULL DEFAULT NULL, -- deletion date is a date time of when the customer needs to be deleted in the database and is required by data protection
  PRIMARY KEY (`CreationID`),
  UNIQUE INDEX `CreationID_UNIQUE` (`CreationID` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

insert into creation (CreationID, CreationDate, DeletionDate) values (1,'2016-12-18','2018-12-18');
insert into creation (CreationID, CreationDate, DeletionDate) values (2,'2017-12-18','2019-12-18');
insert into creation (CreationID, CreationDate, DeletionDate) values (3,'2018-12-18','2020-12-18');
insert into creation (CreationID, CreationDate, DeletionDate) values (4,'2018-11-18','2020-11-18');
insert into creation (CreationID, CreationDate, DeletionDate) values (5,'2017-12-6','2019-12-6');
-- -----------------------------------------------------
-- Table `asg`.`drone`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`drone` ;

CREATE TABLE IF NOT EXISTS `asg`.`drone` (
  `DroneID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, -- surrogate primary key to uniquely identify the drone entity in the drone table 
  `Manufacturer` VARCHAR(45) NULL DEFAULT NULL, -- can enter a manufacture of the drone in a range up to 45 characters. 45 is arbitary and to save space
  `Model` VARCHAR(45) NULL DEFAULT NULL, -- can enter a model of the drone in a range up to 45 characters. 45 is arbitary and to save space
  PRIMARY KEY (`DroneID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

Insert into drone (DroneID, Manufacturer, Model) values (1,'AAI Corporation','model 1');
Insert into drone (DroneID, Manufacturer, Model) values (2,'Delair','model 2');
Insert into drone (DroneID, Manufacturer, Model) values (3,'Parrot SA','model 3');
Insert into drone (DroneID, Manufacturer, Model) values (4,'Sky-Watch','model 4');
Insert into drone (DroneID, Manufacturer, Model) values (5,'XMobots','model 5');


-- -----------------------------------------------------
-- Table `asg`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`customer` ;

CREATE TABLE IF NOT EXISTS `asg`.`customer` (
  `CandidateReferenceID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, -- surrogate primary key to uniquley identify the customer 
  `FirstName` VARCHAR(45) NOT NULL, -- required field to know the admin's first name of a range up to 45 characters. 45 is an arbitrary number to save space 
  `LastName` VARCHAR(45) NOT NULL, -- required field to know the admin's last name of a range up to 45 characters. 45 is an arbitrary number to save space 
  `DateOfBirth` DATE NOT NULL, -- required field to know customer's age. This is a date in the form of yyyy-mm-dd
  `Email` VARCHAR(128) NOT NULL, -- required field to know the customer's email address this is require and not null. It can be a range of characters upto 128
  `PhoneNumber` CHAR(11) NOT NULL, -- require field and has to be exactly 11 characters because the UK standard phone number has exactly 11 digits 
  `Paid` TINYINT(1) NOT NULL, -- this is required to see if the customer has paid to sign up to the course. This is a tiny int as the option is true or false
  `HoursOfFlying` INT(11) NOT NULL, -- this is required for the customer to enter there number of previous flying hours. Thi will be to the nearest full number
  `Disability` TEXT NULL DEFAULT NULL, -- this can be null as not all customers have a disability 
  `EnglishSpeakingLevel` FLOAT NOT NULL, -- this is required so is set to not null 
  `PreferredGSLocation` TEXT NULL DEFAULT NULL, -- this is optional as the user does not have to enter their preffered location for courses 
  `Insured` TINYINT(1) NULL DEFAULT NULL, -- this is an optional field if the customer has a drone therefore can be null
  `Verified` TINYINT(1) NULL DEFAULT '0', -- this field will automatically be set to 0 when a new customer is created and changed when the customer is verified 
  `drone_DroneID` INT(10) UNSIGNED NOT NULL, -- foreign key to link the customer table to drone table. Can be null if customer doesn't have a drone
  `address_AddressID` INT(10) UNSIGNED NOT NULL, -- foreign key to link the customer table to the address table. This is required and cannot be null
  `course_CourseID` INT(10) UNSIGNED NULL DEFAULT NULL, -- Can be null as a customer does not have to sign up to a course straight away
  `creation_CreationID` INT(10) UNSIGNED NOT NULL, -- foreign key to link customer table to creation table in order to keep to the data protection act
  `login_LoginID` INT(10) UNSIGNED NOT NULL, -- foreign key to link the customer table to login table. This is required for the user to get access to their account 
  PRIMARY KEY (`CandidateReferenceID`),
  UNIQUE INDEX `CandidateReferenceID_UNIQUE` (`CandidateReferenceID` ASC),
  INDEX `fk_customer_drone1_idx` (`drone_DroneID` ASC),
  INDEX `fk_customer_address1_idx` (`address_AddressID` ASC),
  INDEX `fk_customer_course1_idx` (`course_CourseID` ASC),
  INDEX `fk_customer_creation1_idx` (`creation_CreationID` ASC),
  INDEX `fk_customer_login1_idx` (`login_LoginID` ASC),
  CONSTRAINT `fk_customer_address1`
    FOREIGN KEY (`address_AddressID`)
    REFERENCES `asg`.`address` (`AddressID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_customer_course1`
    FOREIGN KEY (`course_CourseID`)
    REFERENCES `asg`.`course` (`CourseID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_customer_creation1`
    FOREIGN KEY (`creation_CreationID`)
    REFERENCES `asg`.`creation` (`CreationID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_customer_drone1`
    FOREIGN KEY (`drone_DroneID`)
    REFERENCES `asg`.`drone` (`DroneID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_customer_login1`
    FOREIGN KEY (`login_LoginID`)
    REFERENCES `asg`.`login` (`LoginID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

insert into customer (CandidateReferenceID, FirstName, LastName, DateOfBirth, Email, PhoneNumber, Paid, HoursOfFlying, Disability, EnglishSpeakingLevel,PreferredGSLocation,Insured, Verified, drone_DroneID, address_AddressID, course_CourseID , creation_CreationID , login_LoginID) values (1, 'Lori', 'Woods','1973-06-22', 'zavysaqopu-6806@yopmail.com','07700900169',1,3,null,6,'Cardiff',1,1,1,4,1,1,6);
insert into customer (CandidateReferenceID, FirstName, LastName, DateOfBirth, Email, PhoneNumber, Paid, HoursOfFlying, Disability, EnglishSpeakingLevel,PreferredGSLocation,Insured, Verified, drone_DroneID, address_AddressID, course_CourseID , creation_CreationID , login_LoginID) values (2, 'Kendra', 'Haas','1985-05-19', 'russanesuh-3978@yopmail.com','07700900548',1,3,null,6,'Cardiff',1,1,2,8,2,2,7);
insert into customer (CandidateReferenceID, FirstName, LastName, DateOfBirth, Email, PhoneNumber, Paid, HoursOfFlying, Disability, EnglishSpeakingLevel,PreferredGSLocation,Insured, Verified, drone_DroneID, address_AddressID, course_CourseID , creation_CreationID , login_LoginID) values (3, 'Mathias', 'Salazar','1990-05-01', 'ruhumyki-0452@yopmail.com','07700900079',1,3,null,6,'Cardiff',1,1,3,5,3,3,8);
insert into customer (CandidateReferenceID, FirstName, LastName, DateOfBirth, Email, PhoneNumber, Paid, HoursOfFlying, Disability, EnglishSpeakingLevel,PreferredGSLocation,Insured, Verified, drone_DroneID, address_AddressID, course_CourseID , creation_CreationID , login_LoginID) values (4, 'Rehan', 'Bradshaw','1990-06-20', 'difeparo-0066@yopmail.com','07700900182',1,3,null,6,'Cardiff',1,1,4,9,4,4,9);
insert into customer (CandidateReferenceID, FirstName, LastName, DateOfBirth, Email, PhoneNumber, Paid, HoursOfFlying, Disability, EnglishSpeakingLevel,PreferredGSLocation,Insured, Verified, drone_DroneID, address_AddressID, course_CourseID , creation_CreationID , login_LoginID) values (5, 'Dylan', 'Brennan','1999-11-10', 'kaddammynne-8650@yopmail.com','07700900420',1,3,null,6,'Cardiff',1,1,4,10,1,5,10);
-- -----------------------------------------------------
-- Table `asg`.`materialised_view_user_report`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`materialised_view_user_report` ;

CREATE TABLE IF NOT EXISTS `asg`.`materialised_view_user_report` (
  `Admins` INT(128) NULL DEFAULT '0',
  `Instructors` INT(128) NULL DEFAULT '0',
  `Customers` INT(128) NULL DEFAULT '0',
  `Updatedon` DATETIME NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `asg`.`materialised_view_customer_information`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`materialised_view_course_information`;

CREATE TABLE IF NOT EXISTS `asg`.`materialised_view_course_information`(
`CourseName` VARCHAR(128) NULL,
`CourseType` VARCHAR(128) NULL,
`TotalCusotmers` INT(128) NULL,
`Instructor` VARCHAR(128) NULL
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `asg`.`result`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`result` ;

CREATE TABLE IF NOT EXISTS `asg`.`result` (
  `ResultID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, -- surrogate primary key to uniquely identify each result entity in the table 
  `Mark` INT(11) NOT NULL, -- this will be a grade out of 50 and is required when entering data into the result table. This has to be a number therefore is an int
  `PassFail` TINYINT(1) NOT NULL, -- this will either be true or false based on the user's mark. therefore the data is stored in tiny int where 1 = pass, 0 = fail
  `TypeOfTest` TEXT NOT NULL, -- this is required to know what test the customer has taken
  `customer_CandidateReferenceID` INT(10) UNSIGNED NOT NULL, -- foreign key to link the result table to the customer taking the test  
  PRIMARY KEY (`ResultID`),
  UNIQUE INDEX `ResultID_UNIQUE` (`ResultID` ASC),
  INDEX `fk_Results_customer1_idx` (`customer_CandidateReferenceID` ASC),
  CONSTRAINT `fk_Results_customer1`
    FOREIGN KEY (`customer_CandidateReferenceID`)
    REFERENCES `asg`.`customer` (`CandidateReferenceID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `asg`.`version`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `asg`.`version` ;

CREATE TABLE IF NOT EXISTS `asg`.`version` (
  `dbversion` INT(10) UNSIGNED NOT NULL,
  `active` TINYINT(4) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`dbversion`),
  UNIQUE INDEX `dbversion_UNIQUE` (`dbversion` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- procedure checkIfCustomerIsVerified
-- -----------------------------------------------------
-- uses a cursor to loop through non-verified users and deletes them from the database 
USE `asg`;
DROP procedure IF EXISTS `asg`.`checkIfCustomerIsVerified`;

DELIMITER $$
USE `asg`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkIfCustomerIsVerified`()
BEGIN
DECLARE finished INTEGER DEFAULT 0;
DECLARE variable_customer_ID INTEGER;

DEClARE customers_cursor CURSOR FOR SELECT CandidateReferenceID FROM customer WHERE Verified=0;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
OPEN customers_cursor;
get_customers_loop: LOOP
FETCH customers_cursor INTO variable_customer_ID;
IF finished = 1 THEN 
				 LEAVE get_customers_loop;
				 END IF;
						DELETE FROM login
                        WHERE LoginID = (SELECT login_loginID FROM customer WHERE CandidateReferenceID=variable_customer_ID);
		 END LOOP get_customers_loop;
         CLOSE customers_cursor;
         DELETE FROM customer
			WHERE Verified = 0;

END$$

DELIMITER ;
-- ----------------- test query ------------------------
-- select * from customer
-- CALL checkIfCustomerIsVerified;
-- select * from customer;
-- -----------------------------------------------------

-- -----------------------------------------------------
-- procedure deleteIfDeletionDateExpires
-- -----------------------------------------------------
-- used to check if the customer has passed their deletion date in the database and if so deletes 
-- from the database due to data protection act not allowing us to keep data for longer than needed
USE `asg`;
DROP procedure IF EXISTS `asg`.`deleteIfDeletionDateExpires`;

DELIMITER $$
USE `asg`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteIfDeletionDateExpires`()
Begin
DECLARE finished INTEGER DEFAULT 0;
DECLARE variable_creation_ID INTEGER;
DEClARE creation_cursor CURSOR FOR SELECT CreationID FROM creation WHERE DeletionDate >= now(); -- loops through all the deletion dates that have been past 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
OPEN creation_cursor;
get_creation_loop: LOOP
FETCH creation_cursor INTO variable_creation_ID;
IF finished = 1 THEN 
				 LEAVE get_creation_loop;
				 END IF;
                 
                 DELETE FROM login
                 WHERE LoginID = (SELECT login_LoginID FROM customer WHERE creation_CreationID = variable_creation_ID); -- deletes the customer's login credentials from the  database so the user can't login
                 DELETE FROM customer
                 WHERE creation_CreationID = (SELECT CreationID FROM creation WHERE CreationID = variable_creation_ID); -- deletes the customer's details from the database 
                 
                 END LOOP get_creation_loop;
                 
         CLOSE creation_cursor;
         DELETE FROM creation
			WHERE DeletionDate >= now(); -- deletes the creation / deletion date from the creation table to stop future cursors looping through uneccesary dates
End$$

DELIMITER ;
-- ---------------- test query -------------------------
-- select * from creation;
-- select * from customer;
-- CALL deleteIfDeletionDateExpires;
-- select * from creation;
-- select * from customer;
-- -----------------------------------------------------

-- -----------------------------------------------------
-- procedure update_materialised_view_user_report
-- -----------------------------------------------------
-- this is used to update the update_materialised_view_user_report with a time stamp of when the view was last updated 
USE `asg`;
DROP procedure IF EXISTS `asg`.`update_materialised_view_user_report`;

DELIMITER $$
USE `asg`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_materialised_view_user_report`()
Begin
INSERT INTO materialised_view_user_report (Admins, Instructors, Customers, Updatedon)
VALUES ((select count(*) from login where Access = 'admin'),
(select count(*) from login where Access = 'instructor'),
(select count(*) from login where Access = 'customer'),
now());
end $$

DELIMITER ;
CALL update_materialised_view_user_report;
select * from materialised_view_user_report;

-- -----------------------------------------------------
-- procedure update_materialised_view_customer_information
-- -----------------------------------------------------
USE `asg`;
DROP procedure IF EXISTS `asg`.`update_materialised_view_course_information`;

DELIMITER $$
USE `asg`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_materialised_view_course_information`()
Begin 
INSERT INTO materialised_view_course_information
SELECT Name as CourseName, 
Type as CourseType,
count(customer.CandidateReferenceID) as TotalCustomers,
concat(instructor.FirstName,' ', instructor.LastName) AS Instructor
from course
join customer on course.CourseID = customer.course_CourseID
join instructor on instructor.InstructorID = course.Instructor_InstructorID 
group by CourseName 
order by course.Date;
end$$
DELIMITER ;
CALL update_materialised_view_course_information;

-- -----------------------------------------------------
-- function show_all_users_access
-- -----------------------------------------------------
-- function that will tell how many users there are with admin, instructor and customer access 
USE `asg`;
DROP function IF EXISTS `asg`.`show_all_users_access`;

DELIMITER $$
USE `asg`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `show_all_users_access`() RETURNS varchar(40000) CHARSET latin1
BEGIN
DECLARE userAccessList varchar(40000);
declare adminCount int;
declare instructorCount int;
declare customerCount int;
SET adminCount = (select count(*) from login WHERE Access = 'admin');
SET instructorCount = (select count(*) from login WHERE Access = 'instructor');
SET customerCount = (select count(*) from login WHERE Access = 'customer');
SET userAccessList = (concat('customers = ',char(10), customerCount ,char(10), ' instructors = ',
instructorCount, char(10),' admin = ',adminCount));
RETURN userAccessList;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function show_user_access
-- -----------------------------------------------------
-- function to find the a specific users access level 
USE `asg`;
DROP function IF EXISTS `asg`.`show_user_access`;

DELIMITER $$
USE `asg`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `show_user_access`(user long) RETURNS varchar(128) CHARSET latin1
BEGIN
DECLARE accessType Varchar(128);
SET accessType = concat('Their access level is:', (select Access from login 
where LoginID = (select login_LoginID from customer WHERE CandidateReferenceID = user))); 
RETURN accessType;
END$$

DELIMITER ;


-- -----------------------------------------------------
-- trigger address_AFTER_INSERT
-- -----------------------------------------------------
-- trigger that updates the version table when there is a change in the address table 
DELIMITER $$

USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`address_AFTER_INSERT` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`address_AFTER_INSERT`
AFTER INSERT ON `asg`.`address`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Inserting new data into address table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger address_AFTER_UPDATE
-- -----------------------------------------------------
-- updates the version table whe n the address table has been updated 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`address_AFTER_UPDATE` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`address_AFTER_UPDATE`
AFTER UPDATE ON `asg`.`address`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Updating data in address table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger login_AFTER_INSERT
-- -----------------------------------------------------
-- trigger to update the version table whenever there is a new insert into the login table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`login_AFTER_INSERT` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`login_AFTER_INSERT`
AFTER INSERT ON `asg`.`login`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Inserting new data into login table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger login_AFTER_UPDATE
-- -----------------------------------------------------
-- updates the version table when there is an update to the  login table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`login_AFTER_UPDATE` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`login_AFTER_UPDATE`
AFTER UPDATE ON `asg`.`login`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Updating data in login table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger administrator_AFTER_INSERT
-- -----------------------------------------------------
-- updates the version table once there is a new inset in the administration table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`administrator_AFTER_INSERT` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`administrator_AFTER_INSERT`
AFTER INSERT ON `asg`.`administrator`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Inserting new data into administrator table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger administrator_AFTER_UPDATE
-- -----------------------------------------------------
-- updates the version table whenever there is an update in the administration table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`administrator_AFTER_UPDATE` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`administrator_AFTER_UPDATE`
AFTER UPDATE ON `asg`.`administrator`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Updating data in administrator table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger qualification_AFTER_INSERT
-- -----------------------------------------------------
-- updates the version table whenever there is a new qulaification added to the qalification table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`qualification_AFTER_INSERT` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`qualification_AFTER_INSERT`
AFTER INSERT ON `asg`.`qualification`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Inserting new data into qualification table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger qualification_AFTER_UPDATE
-- -----------------------------------------------------
-- updates the version table whenever there is an update in the qualification table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`qualification_AFTER_UPDATE` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`qualification_AFTER_UPDATE`
AFTER UPDATE ON `asg`.`qualification`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Updating data in qualification table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger instructor_AFTER_INSERT
-- -----------------------------------------------------
-- updates the version table whenever there is an insert into the instructor table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`instructor_AFTER_INSERT` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`instructor_AFTER_INSERT`
AFTER INSERT ON `asg`.`instructor`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Inserting new data into instructor table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger instructor_AFTER_UPDATE
-- -----------------------------------------------------
-- updates the version table whenever there is an update in the instructor table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`instructor_AFTER_UPDATE` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`instructor_AFTER_UPDATE`
AFTER UPDATE ON `asg`.`instructor`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Updating data in instructor table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger course_AFTER_INSERT
-- -----------------------------------------------------
-- updates the version table whenver there is an insert into the course table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`course_AFTER_INSERT` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`course_AFTER_INSERT`
AFTER INSERT ON `asg`.`course`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Inserting new data into course table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger course_AFTER_UPDATE
-- -----------------------------------------------------
-- updates the version table t=whenever there is a update in the course table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`course_AFTER_UPDATE` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`course_AFTER_UPDATE`
AFTER UPDATE ON `asg`.`course`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Updating data in course table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger drone_AFTER_INSERT
-- -----------------------------------------------------
-- updates the version table whevere there is a new insert in the drone table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`drone_AFTER_INSERT` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`drone_AFTER_INSERT`
AFTER INSERT ON `asg`.`drone`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Inserting new data into drone table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger drone_AFTER_UPDATE
-- -----------------------------------------------------
-- updates the version table whevere there is an update in the drone table
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`drone_AFTER_UPDATE` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`drone_AFTER_UPDATE`
AFTER UPDATE ON `asg`.`drone`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Updating data in drone table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger customer_AFTER_INSERT
-- -----------------------------------------------------
-- updates the version control whenever there is an insert into the customer table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`customer_AFTER_INSERT` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`customer_AFTER_INSERT`
AFTER INSERT ON `asg`.`customer`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Inserting new data into customer table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger customer_AFTER_UPDATE
-- -----------------------------------------------------
-- updates the version control whenever there is an update in the customer table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`customer_AFTER_UPDATE` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`customer_AFTER_UPDATE`
AFTER UPDATE ON `asg`.`customer`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Updating data in customer table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger results_AFTER_INSERT
-- -----------------------------------------------------
-- updates the version table wher=never there is an inset in the results table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`results_AFTER_INSERT` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`results_AFTER_INSERT`
AFTER INSERT ON `asg`.`result`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Inserting new data into results table');
END IF;

END$$

-- -----------------------------------------------------
-- trigger results_AFTER_UPDATE
-- -----------------------------------------------------
-- updates the version table wher=never there is an update in the results table 
USE `asg`$$
DROP TRIGGER IF EXISTS `asg`.`results_AFTER_UPDATE` $$
USE `asg`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `asg`.`results_AFTER_UPDATE`
AFTER UPDATE ON `asg`.`result`
FOR EACH ROW
BEGIN

DECLARE oldVersion INTEGER DEFAULT (SELECT dbversion FROM version WHERE active = true);
DECLARE newVersion INTEGER DEFAULT 2;

IF EXISTS (SELECT dbversion FROM version WHERE dbversion = oldVersion AND active = true)
THEN
SET newVersion = oldVersion + 1;
UPDATE version SET active = false WHERE dbversion = oldVersion AND active = true;
INSERT INTO version (dbversion, active, description) VALUES (newversion, true, 'Updating data in results table');
END IF;

END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


SELECT * FROM address;
select * from creation;
select * from customer;
select * from result;
select * from instructor;
select * from administrator;
select * from qualification;
select * from course;
select * from drone;
select * from login;