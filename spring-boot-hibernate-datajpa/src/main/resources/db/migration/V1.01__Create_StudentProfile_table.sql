CREATE TABLE `student_profile` (
  `id` BIGINT NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `studentProfile_id_student_id`
    FOREIGN KEY (`id`)
    REFERENCES `mytestdb`.`student` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
