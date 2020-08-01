-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema redditreader
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `redditreader` ;

-- -----------------------------------------------------
-- Schema redditreader
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `redditreader` DEFAULT CHARACTER SET utf8mb4 ;
SHOW WARNINGS;
USE `redditreader` ;

-- -----------------------------------------------------
-- Table `redditreader`.`Account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `redditreader`.`Account` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `redditreader`.`Account` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nickname` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `user_UNIQUE` (`username` ASC) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `redditreader`.`Host`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `redditreader`.`Host` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `redditreader`.`Host` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `extraction_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) ,
  UNIQUE INDEX `url_UNIQUE` (`url` ASC) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `redditreader`.`board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `redditreader`.`board` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `redditreader`.`board` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Host_id` INT UNSIGNED NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`, `Host_id`),
  UNIQUE INDEX `path_UNIQUE` (`url` ASC) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_Feed_Host1_idx` (`Host_id` ASC) ,
  CONSTRAINT `fk_Feed_Host1`
    FOREIGN KEY (`Host_id`)
    REFERENCES `redditreader`.`Host` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `redditreader`.`Image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `redditreader`.`Image` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `redditreader`.`Image` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Feed_id` INT UNSIGNED NOT NULL,
  `title` VARCHAR(1000) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `local_path` VARCHAR(255) NOT NULL,
  `date` DATETIME NOT NULL,
  PRIMARY KEY (`id`, `Feed_id`),
  INDEX `fk_Image_Feed_idx` (`Feed_id` ASC) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `path_UNIQUE` (`url` ASC) ,
  UNIQUE INDEX `local_path_UNIQUE` (`local_path` ASC) ,
  CONSTRAINT `fk_Image_Feed`
    FOREIGN KEY (`Feed_id`)
    REFERENCES `redditreader`.`board` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
SET SQL_MODE = '';
-- DROP USER IF EXISTS cst8288@localhost;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER IF NOT EXISTS 'cst8288'@'localhost' IDENTIFIED BY '8288';
GRANT ALL ON `redditreader`.* TO 'cst8288'@'localhost';
SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `redditreader`.`Account`
-- -----------------------------------------------------
START TRANSACTION;
USE `redditreader`;
INSERT INTO `redditreader`.`Account` (`id`, `nickname`, `username`, `password`) VALUES (1, 'administrator', 'admin', 'admin');
INSERT INTO `redditreader`.`Account` (`id`, `nickname`, `username`, `password`) VALUES (2, 'Shawn', 'cst8288', '8288');

COMMIT;


-- -----------------------------------------------------
-- Data for table `redditreader`.`Host`
-- -----------------------------------------------------
START TRANSACTION;
USE `redditreader`;
INSERT INTO `redditreader`.`Host` (`id`, `name`, `url`, `extraction_type`) VALUES (1, 'Reddit', 'https://www.reddit.com/', 'json');

COMMIT;


-- -----------------------------------------------------
-- Data for table `redditreader`.`board`
-- -----------------------------------------------------
START TRANSACTION;
USE `redditreader`;
INSERT INTO `redditreader`.`board` (`id`, `Host_id`, `url`, `name`) VALUES (1, 1, 'https://www.reddit.com/r/EarthPorn.json', 'EarthPorn');
INSERT INTO `redditreader`.`board` (`id`, `Host_id`, `url`, `name`) VALUES (2, 1, 'https://www.reddit.com/r/Art.json', 'Art');
INSERT INTO `redditreader`.`board` (`id`, `Host_id`, `url`, `name`) VALUES (3, 1, 'https://www.reddit.com/r/wallpapers.json', 'Wallpapers');
INSERT INTO `redditreader`.`board` (`id`, `Host_id`, `url`, `name`) VALUES (4, 1, 'https://www.reddit.com/r/wallpaper.json', 'Wallpaper');

COMMIT;

