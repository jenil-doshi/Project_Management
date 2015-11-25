CREATE TABLE `project` (
  `PID` int(11) NOT NULL AUTO_INCREMENT,
  `ProjectName` varchar(45) DEFAULT NULL,
  `ProjectDescription` varchar(45) DEFAULT NULL,
  `Status` varchar(45) DEFAULT NULL,
  `Owner` int(11) DEFAULT NULL,
  `StartDate` datetime DEFAULT NULL,
  `EndDate` datetime DEFAULT NULL,
  PRIMARY KEY (`PID`),
  KEY `UID_FK_idx` (`Owner`),
  CONSTRAINT `UID_FK` FOREIGN KEY (`Owner`) REFERENCES `user` (`UID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

CREATE TABLE `Task` (
  `TID` int(11) NOT NULL AUTO_INCREMENT,
  `ActualTime` int(11) DEFAULT '0',
  `EstimatedUnits` int(11) DEFAULT '0',
  `TaskName` varchar(255) DEFAULT NULL,
  `State` varchar(255) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Assignee` int(11) DEFAULT '0',
  `Project` int(11) DEFAULT NULL,
  PRIMARY KEY (`TID`),
  KEY `ASSIGNEE_ID_FK_idx` (`Assignee`),
  KEY `PROJ_ID_FK_idx` (`Project`),
  CONSTRAINT `ASSIGNEE_ID_FK` FOREIGN KEY (`Assignee`) REFERENCES `User` (`UID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `PROJ_ID_FK` FOREIGN KEY (`Project`) REFERENCES `Project` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `UID` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`UID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `user_project_info` (
  `UID` int(11) NOT NULL,
  `PID` int(11) NOT NULL,
  `AcceptanceStatus` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`UID`,`PID`),
  KEY `_idx` (`UID`),
  KEY `Project_ID_idx` (`PID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_roles` (
  `username` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `uni_username_role` (`role`,`username`),
  KEY `fk_username_idx` (`username`),
  CONSTRAINT `fk_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `users` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
