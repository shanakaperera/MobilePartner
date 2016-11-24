SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mobile_partner` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mobile_partner` ;

-- -----------------------------------------------------
-- Table `mobile_partner`.`inbound_message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mobile_partner`.`inbound_message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `message` TEXT NULL,
  `originator` VARCHAR(45) NULL,
  `mem_index` INT NULL,
  `mem_location` TEXT NULL,
  `mp_ref_no` INT NULL,
  `mp_max_no` INT NULL,
  `mp_seq_no` INT NULL,
  `mp_mem_index` INT NULL,
  `date_recived` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `is_read` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mobile_partner`.`message_define`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mobile_partner`.`message_define` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type_name` VARCHAR(45) NULL,
  `description` TEXT NULL,
  `date_added` DATE NULL,
  `date_updated` DATE NULL,
  `status` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `type_name_UNIQUE` (`type_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mobile_partner`.`saved_contacts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mobile_partner`.`saved_contacts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `contact_name` VARCHAR(45) NULL,
  `number` VARCHAR(45) NULL,
  `description` TEXT NULL,
  `date_added` DATE NULL,
  `date_updated` DATE NULL,
  `status` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC),
  UNIQUE INDEX `contact_name_UNIQUE` (`contact_name` ASC))
ENGINE = InnoDB;


SET SQL_MODE='';
SET FOREIGN_KEY_CHECKS=1;
SET UNIQUE_CHECKS=1;