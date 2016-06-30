-- Author: 	
-- NetID: 	
--
-- Database: planner
--

DROP DATABASE IF EXISTS `planner`;
CREATE DATABASE IF NOT EXISTS `planner` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `planner`;

-- --------------------------------------------------------
--
-- Table structure for table
--

CREATE TABLE IF NOT EXISTS Department
(
	DeptId smallint(6) NOT NULL auto_increment primary key,
	DeptName varchar(50),
    	DeptInit char(4)
);

CREATE TABLE IF NOT EXISTS DegreePlan
(
	DegId smallint(6) not null auto_increment primary key,
    	DegName varchar(50),
    	DeptId smallint(6),   
    FOREIGN KEY (`DeptId`) REFERENCES Department(DeptId)
);

CREATE TABLE IF NOT EXISTS User
(
  UserId smallint(6) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Name varchar(50) NOT NULL,
  Username varchar(32) NOT NULL,
  Password varchar(32) NOT NULL,
  NetID varchar(32) NOT NULL,
  DegId smallint(6),
  FOREIGN KEY (`DegId`) REFERENCES DegreePlan(`DegId`)
);

CREATE TABLE IF NOT EXISTS Class
(
    ClassId int primary key,
    ClassName varchar(100),
    ClassPrfx char(4),
    ClassHours int
);

CREATE TABLE IF NOT EXISTS TrackCourse
(
    ClassId int,
    DegId smallint(6),
    CourseType varchar(32) NOT NULL,  
    FOREIGN KEY (`ClassId`) REFERENCES Class(ClassId),
    FOREIGN KEY (`DegId`) REFERENCES DegreePlan(DegId)
);

CREATE TABLE IF NOT EXISTS Grade
(
	GradeId smallint(6) not null auto_increment primary key,
    	ClassId int,
    	UserId smallint(6),
    	Passed smallint(6),
    	Grade decimal(18,2),
    	GpaWeight decimal(18,2),
    FOREIGN KEY (ClassId) REFERENCES Class(ClassId),
    FOREIGN KEY (UserId) REFERENCES User(UserId)
);

-- 
-- Data for Tables
-- 

INSERT INTO Department (`DeptName`, `DeptInit`) VALUES 
	('Computer Science', 'CS');

INSERT INTO DegreePlan(`DegName`, `DeptId`) VALUES	
	('Tranditional CS', 1),
	('Data Science', 1),
	('Networks & Telecommunications', 1),
	('Intelligent Systems', 1),
	('SYSTEMS', 1),
	('Software Engineering', 1),
	('Interactive Computing', 1),
	('Information Assurance', 1);

INSERT INTO User (`Name`,`Username`, `Password`,`NetID`,`DegId`) VALUES
	('Nick Young', 'nick', 'nick123', 'nxy150831',1),
    ('Kobe Bryant', 'kobe', 'kobe123', 'kxb150832',2),
    ('Steve Curry', 'student', 'student', 'sxc150833',1),
    ('Hongyi Wang', 'admin', 'admin', 'hxw150830',2);

INSERT INTO Class (`ClassId`,`ClassName`,`ClassPrfx`, `ClassHours`) VALUES
	(5301,'Professional and Technical Communication', 'CS',3),
	(5333,'Discrete Structures', 'CS',3),
	(5336,'Programming Projects in Java', 'CS',3),
	(5343,'Algorithm Analysis and Data Structures', 'CS',3),
	(5348,'Operating Systems Concepts', 'CS',3),
	(5349,'Automata Theory', 'CS',3),
	(5375,'Principles of UNIX', 'CS',3),
	(5390,'Computer Networks', 'CS',3),
	(6301,'Special Topics in Computer Science - Big Data Management and Analytics', 'CS',3),
	(6304,'Computer Architecture', 'CS',3),
	(6316,'Agile Methods', 'CS',3),
	(6320,'Natural Language Processing', 'CS',3),
	(6321,'Discourse Processing', 'CS',3),
	(6322,'Information Retrieval', 'CS',3),
	(6324,'Information Security', 'CS',3),
	(6325,'Introduction to Bioinformatics', 'CS',3),
	(6349,'Network Security', 'CS',3),
	(6352,'Performance of Computer Systems and Networks', 'CS',3),
	(6353,'Compiler Construction', 'CS',3),
	(6354,'Advanced Software Engineering', 'CS',3),
	(6356,'Software Maintenance, Evolution, and Re-Engineering', 'CS',3),
	(6359,'Object-Oriented Analysis and Design', 'CS',3),
	(6360,'Database Design', 'CS',3),
	(6361,'Advanced Requirements Engineering', 'CS',3),
	(6362,'Advanced Software Architecture and Design', 'CS',3),
	(6363,'Design and Analysis of Computer Algorithms', 'CS',3),
	(6364,'Artificial Intelligence', 'CS',3),
	(6366,'Computer Graphics', 'CS',3),
	(6367,'Software Testing and Verification', 'CS',3),
	(6368,'Telecommunication Network Management', 'CS',3),
	(6371,'Advanced Programming Languages', 'CS',3),
	(6373,'Intelligent Systems', 'CS',3),
	(6374,'Computational Logic', 'CS',3),
	(6375,'Machine Learning', 'CS',3),
	(6377,'Introduction to Cryptography', 'CS',3),
	(6378,'Advanced Operating Systems', 'CS',3),
	(6380,'Distributed Computing', 'CS',3),
	(6381,'Combinatorics and Graph Algorithms', 'CS',3),
	(6382,'Theory of Computation', 'CS',3),
	(6383,'Computational Systems Biology', 'CS',3),
	(6384,'Computer Vision', 'CS',3),
	(6385,'Algorithmic Aspects of Telecommunication Networks', 'CS',3),
	(6386,'Telecommunication Software Design', 'CS',3),
	(6387,'Advanced Software Engineering Project', 'CS',3),
	(6388,'Software Project Planning and Management', 'CS',3),
	(6390,'Advanced Computer Networks', 'CS',3),
	(6391,'Optical Networks', 'CS',3),
	(6392,'Mobile Computing Systems', 'CS',3),
	(6395,'Speech Recognition, Synthesis, and Understanding', 'CS',3),
	(6396,'Real-Time Systems', 'CS',3),
	(6397,'Synthesis and Optimization of High-Performance Systems', 'CS',3),
	(6398,'DSP Architectures', 'CS',3),
	(6399,'Parallel Architectures and Systems', 'CS',3);

INSERT INTO Grade (`ClassId`,`UserId`,`Passed`,`Grade`,`GpaWeight`) VALUES
	(5301,1,1,85,3.0),
	(5333,1,1,95,4.0),	
	(5343,1,1,70,2.0),
	(6349,1,1,85,3.5),
	(6363,1,1,85,3.5),

	(6301,2,1,85,3.0),
	(6304,2,1,95,4.0),	
	(6390,2,1,70,2.0),
	(6396,2,1,85,3.5);

INSERT INTO TrackCourse (`ClassId`,`DegId`,`CourseType`) VALUES
	(5301,1,'pre'),
	(5333,1,'pre'),
	(5343,1,'pre'),
	(6349,1,'core'),
	(6363,1,'core'),
	(6320,1,'core'),
	(6354,1,'core'),
	(6392,1,'elective'),
	(6397,1,'elective'),
	(6398,1,'elective'),
	(6399,1,'elective'),

	(5301,2,'pre'),
	(5375,2,'pre'),
	(5343,2,'pre'),
	(6301,2,'core'),
	(6363,2,'core'),
	(6304,2,'core'),
	(6354,2,'core'),
	(6390,2,'elective'),
	(6396,2,'elective'),
	(6398,2,'elective'),
	(6399,2,'elective');


CREATE TABLE IF NOT EXISTS TrackCourse
(
    ClassId int,
    DegId smallint(6),    
    FOREIGN KEY (`ClassId`) REFERENCES Class(ClassId),
    FOREIGN KEY (`DegId`) REFERENCES DegreePlan(DegId)
);





-- 
-- CREATE VIEWS
-- 

-- SELECT * FROM `vwClassesPassed` WHERE vwClassesPassed.userID=1;
CREATE VIEW vwClassesPassed
AS 
SELECT 	US.UserId,
		DP.DegName,
        GR.Grade,
        GR.GpaWeight,
        CS.ClassId,
        CS.ClassName,
        CS.ClassHours

FROM 	planner.User US
		INNER JOIN planner.DegreePlan DP
			ON US.DegId = DP.DegId
		INNER JOIN planner.Grade 		GR
			ON US.UserId = GR.UserId
		INNER JOIN planner.Class 		CS
			ON GR.ClassId = CS.ClassId
WHERE	GR.Passed = 1;


-- SELECT * FROM vwClassesRequired WHERE vwClassesRequired.userid=2;
CREATE VIEW vwClassesRequired
AS
SELECT 	US.UserId,
		DP.DegName,
        CS.ClassId,
        CS.ClassName,
        CS.ClassHours

FROM 	planner.User US
		INNER JOIN planner.DegreePlan DP
			ON US.DegId = DP.DegId
		LEFT JOIN planner.TrackCourse TC
			ON DP.DegId = TC.DegId
		LEFT JOIN planner.Class 		CS
			ON TC.ClassId = CS.ClassId
		LEFT JOIN planner.Grade 		GR
			ON 	CS.ClassId = GR.ClassId
			AND US.UserId = GR.UserId
WHERE	IFNULL(GR.Passed,0) = 0 
        AND NOT EXISTS(SELECT NULL FROM planner.Grade g2 WHERE CS.ClassId = g2.ClassId AND US.UserId = g2.UserId AND Passed = 1 );
		

-- SELECT * FROM  `wvGPA` WHERE wvGPA.userID=2;
CREATE VIEW wvGPA
AS
SELECT  US.Name,
		US.UserID,
        ROUND(AVG(GR.GpaWeight),2) AS GPA
FROM 	planner.User US
        INNER JOIN planner.Grade 		GR
			ON US.UserId = GR.UserId
GROUP
BY      US.Name, US.UserID;


