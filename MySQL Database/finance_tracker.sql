-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema finance_tracker
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema finance_tracker
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `finance_tracker` DEFAULT CHARACTER SET utf8 ;
USE `finance_tracker` ;

-- -----------------------------------------------------
-- Table `finance_tracker`.`currencies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`currencies` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `email` VARCHAR(25) NOT NULL,
  `currency_id` INT(11) NOT NULL,
  `isAdmin` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `fk_users_currencies1_idx` (`currency_id` ASC),
  CONSTRAINT `fk_users_currencies`
    FOREIGN KEY (`currency_id`)
    REFERENCES `finance_tracker`.`currencies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`accounts` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `balance` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_accounts_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_accounts_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `finance_tracker`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`budget_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`budget_types` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`repeat_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`repeat_types` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`budgets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`budgets` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `for` VARCHAR(45) NOT NULL,
  `begin_date` DATE NULL DEFAULT NULL,
  `end_date` DATE NULL DEFAULT NULL,
  `amount` INT(11) NOT NULL,
  `currency_id` INT(11) NOT NULL,
  `budget_type_id` INT(11) NOT NULL,
  `repeat_type_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_budgets_currencies1_idx` (`currency_id` ASC),
  INDEX `fk_budgets_budget_types1_idx` (`budget_type_id` ASC),
  INDEX `fk_budgets_repeat_types1_idx` (`repeat_type_id` ASC),
  INDEX `fk_budgets_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_budgets_budget_types1`
    FOREIGN KEY (`budget_type_id`)
    REFERENCES `finance_tracker`.`budget_types` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_budgets_currencies1`
    FOREIGN KEY (`currency_id`)
    REFERENCES `finance_tracker`.`currencies` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_budgets_repeat_types1`
    FOREIGN KEY (`repeat_type_id`)
    REFERENCES `finance_tracker`.`repeat_types` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_budgets_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `finance_tracker`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`finance_operation_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`finance_operation_types` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finance_tracker`.`catgories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`catgories` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `for_type_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_catgories_finance_operation_types1_idx` (`for_type_id` ASC),
  CONSTRAINT `fk_catgories_finance_operation_types1`
    FOREIGN KEY (`for_type_id`)
    REFERENCES `finance_tracker`.`finance_operation_types` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`tags` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `for_type_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tags_finance_operation_types1_idx` (`for_type_id` ASC),
  CONSTRAINT `fk_tags_finance_operation_types1`
    FOREIGN KEY (`for_type_id`)
    REFERENCES `finance_tracker`.`finance_operation_types` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`finance_operations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`finance_operations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `amount` INT(11) NOT NULL,
  `currency_id` INT(11) NOT NULL,
  `account_id` INT(11) NOT NULL,
  `date` DATE NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  `repeat_type_id` INT(11) NOT NULL,
  `catgory_id` INT(11) NOT NULL,
  `finance_operation_type_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_expenses_currencies1_idx` (`currency_id` ASC),
  INDEX `fk_expenses_accounts1_idx` (`account_id` ASC),
  INDEX `fk_expenses_repeat_types1_idx` (`repeat_type_id` ASC),
  INDEX `fk_expenses_expense_catgories1_idx` (`catgory_id` ASC),
  INDEX `fk_finance_operations_finance_operation_types1_idx` (`finance_operation_type_id` ASC),
  CONSTRAINT `fk_expenses_accounts1`
    FOREIGN KEY (`account_id`)
    REFERENCES `finance_tracker`.`accounts` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_expenses_currencies1`
    FOREIGN KEY (`currency_id`)
    REFERENCES `finance_tracker`.`currencies` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_expenses_expense_catgories1`
    FOREIGN KEY (`catgory_id`)
    REFERENCES `finance_tracker`.`catgories` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_expenses_repeat_types1`
    FOREIGN KEY (`repeat_type_id`)
    REFERENCES `finance_tracker`.`repeat_types` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_finance_operations_finance_operation_types1`
    FOREIGN KEY (`finance_operation_type_id`)
    REFERENCES `finance_tracker`.`finance_operation_types` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`finance_operation_has_tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`finance_operation_has_tags` (
  `finance_operation_id` INT(11) NOT NULL,
  `tag_id` INT(11) NOT NULL,
  INDEX `fk_expenses_has_tags_expenses1_idx` (`finance_operation_id` ASC),
  INDEX `fk_expenses_has_tags_expense_tags1_idx` (`tag_id` ASC),
  CONSTRAINT `fk_expenses_has_tags_expense_tags1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `finance_tracker`.`tags` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_expenses_has_tags_expenses1`
    FOREIGN KEY (`finance_operation_id`)
    REFERENCES `finance_tracker`.`finance_operations` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
