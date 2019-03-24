CREATE TABLE `user_profile` (
  `id` BIGINT NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `userProfile_id_user_id`
    FOREIGN KEY (`id`)
    REFERENCES `mytestdb`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
