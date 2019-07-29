CREATE TABLE `student_verification` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `verification_code` VARCHAR(45) NOT NULL,
  `creation_time` DATETIME NOT NULL,
  `student_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `studentVerification_studentId_student_id_idx` (`student_id` ASC),
  CONSTRAINT `studentVerification_studentId_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `student` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
