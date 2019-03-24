CREATE TABLE `user_verification` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `verification_code` VARCHAR(45) NOT NULL,
  `creation_time` DATETIME NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `userVerification_userId_user_id_idx` (`user_id` ASC),
  CONSTRAINT `userVerification_userId_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
