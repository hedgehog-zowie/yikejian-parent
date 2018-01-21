DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` INT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(32) NOT NULL,
  `authorities` TEXT NOT NULL,
  `effective` INT(1) NOT NULL DEFAULT '1',
  `deleted` INT(1) NOT NULL DEFAULT '0',
  `created_by` VARCHAR(16) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` VARCHAR(16) NOT NULL,
  `last_modified_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
CREATE TABLE `user` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(32) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `role_id` INT(11) NOT NULL,
  `effective` INT(1) NOT NULL DEFAULT '1',
  `deleted` INT(1) NOT NULL DEFAULT '0',
  `created_by` VARCHAR(16) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` VARCHAR(16) NOT NULL,
  `last_modified_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  KEY `fk_user_role_id` (`role_id`),
  CONSTRAINT `fk_user_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;
