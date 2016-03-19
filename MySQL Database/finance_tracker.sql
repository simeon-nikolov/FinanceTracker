-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema finance_tracker
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema finance_tracker
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `finance_tracker` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `finance_tracker` ;

-- -----------------------------------------------------
-- Table `finance_tracker`.`currencies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`currencies` (
  `id` INT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finance_tracker`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`users` (
  `id` INT NULL AUTO_INCREMENT COMMENT '',
  `username` VARCHAR(20) NOT NULL COMMENT '',
  `password` VARCHAR(50) NOT NULL COMMENT '',
  `email` VARCHAR(25) NOT NULL COMMENT '',
  `currencies_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `username_UNIQUE` (`username` ASC)  COMMENT '',
  UNIQUE INDEX `email_UNIQUE` (`email` ASC)  COMMENT '',
  INDEX `fk_users_currencies1_idx` (`currencies_id` ASC)  COMMENT '',
  CONSTRAINT `fk_users_currencies1`
    FOREIGN KEY (`currencies_id`)
    REFERENCES `finance_tracker`.`currencies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finance_tracker`.`accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`accounts` (
  `id` INT NULL AUTO_INCREMENT COMMENT '',
  `title` VARCHAR(45) NOT NULL COMMENT '',
  `balance` INT NOT NULL COMMENT '',
  `accountscol` VARCHAR(45) NULL COMMENT '',
  `user_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_accounts_users1_idx` (`user_id` ASC)  COMMENT '',
  CONSTRAINT `fk_accounts_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `finance_tracker`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finance_tracker`.`budget_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`budget_types` (
  `id` INT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finance_tracker`.`repeat_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`repeat_types` (
  `id` INT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finance_tracker`.`budgets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`budgets` (
  `id` INT NULL AUTO_INCREMENT COMMENT '',
  `for` VARCHAR(45) NOT NULL COMMENT '',
  `begin_date` DATE NULL COMMENT '',
  `end_date` DATE NULL COMMENT '',
  `amount` INT NOT NULL COMMENT '',
  `currency_id` INT NOT NULL COMMENT '',
  `budget_type_id` INT NOT NULL COMMENT '',
  `repeat_type_id` INT NOT NULL COMMENT '',
  `users_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_budgets_currencies1_idx` (`currency_id` ASC)  COMMENT '',
  INDEX `fk_budgets_budget_types1_idx` (`budget_type_id` ASC)  COMMENT '',
  INDEX `fk_budgets_repeat_types1_idx` (`repeat_type_id` ASC)  COMMENT '',
  INDEX `fk_budgets_users1_idx` (`users_id` ASC)  COMMENT '',
  CONSTRAINT `fk_budgets_currencies1`
    FOREIGN KEY (`currency_id`)
    REFERENCES `finance_tracker`.`currencies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_budgets_budget_types1`
    FOREIGN KEY (`budget_type_id`)
    REFERENCES `finance_tracker`.`budget_types` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_budgets_repeat_types1`
    FOREIGN KEY (`repeat_type_id`)
    REFERENCES `finance_tracker`.`repeat_types` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_budgets_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `finance_tracker`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finance_tracker`.`catgories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`catgories` (
  `id` INT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finance_tracker`.`expenses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`expenses` (
  `id` INT NULL AUTO_INCREMENT COMMENT '',
  `amount` INT NOT NULL COMMENT '',
  `currency_id` INT NOT NULL COMMENT '',
  `catgory_id` INT NOT NULL COMMENT '',
  `account_id` INT NOT NULL COMMENT '',
  `date` DATE NOT NULL COMMENT '',
  `description` VARCHAR(200) NOT NULL COMMENT '',
  `repeat_type_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_expenses_currencies1_idx` (`currency_id` ASC)  COMMENT '',
  INDEX `fk_expenses_catgories1_idx` (`catgory_id` ASC)  COMMENT '',
  INDEX `fk_expenses_accounts1_idx` (`account_id` ASC)  COMMENT '',
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)  COMMENT '',
  INDEX `fk_expenses_repeat_types1_idx` (`repeat_type_id` ASC)  COMMENT '',
  CONSTRAINT `fk_expenses_currencies1`
    FOREIGN KEY (`currency_id`)
    REFERENCES `finance_tracker`.`currencies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_expenses_catgories1`
    FOREIGN KEY (`catgory_id`)
    REFERENCES `finance_tracker`.`catgories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_expenses_accounts1`
    FOREIGN KEY (`account_id`)
    REFERENCES `finance_tracker`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_expenses_repeat_types1`
    FOREIGN KEY (`repeat_type_id`)
    REFERENCES `finance_tracker`.`repeat_types` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finance_tracker`.`tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`tags` (
  `id` INT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finance_tracker`.`expenses_has_tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`expenses_has_tags` (
  `expense_id` INT NOT NULL COMMENT '',
  `tag_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`expense_id`, `tag_id`)  COMMENT '',
  INDEX `fk_expenses_has_tags_tags1_idx` (`tag_id` ASC)  COMMENT '',
  INDEX `fk_expenses_has_tags_expenses1_idx` (`expense_id` ASC)  COMMENT '',
  CONSTRAINT `fk_expenses_has_tags_expenses1`
    FOREIGN KEY (`expense_id`)
    REFERENCES `finance_tracker`.`expenses` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_expenses_has_tags_tags1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `finance_tracker`.`tags` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finance_tracker`.`incomes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`incomes` (
  `id` INT NULL AUTO_INCREMENT COMMENT '',
  `amount` INT NOT NULL COMMENT '',
  `currency_id` INT NOT NULL COMMENT '',
  `catgory_id` INT NOT NULL COMMENT '',
  `account_id` INT NOT NULL COMMENT '',
  `date` DATE NOT NULL COMMENT '',
  `description` VARCHAR(200) NOT NULL COMMENT '',
  `repeat_type_id` INT NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_expenses_currencies1_idx` (`currency_id` ASC)  COMMENT '',
  INDEX `fk_expenses_catgories1_idx` (`catgory_id` ASC)  COMMENT '',
  INDEX `fk_expenses_accounts1_idx` (`account_id` ASC)  COMMENT '',
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)  COMMENT '',
  INDEX `fk_expenses_repeat_types1_idx` (`repeat_type_id` ASC)  COMMENT '',
  CONSTRAINT `fk_expenses_currencies10`
    FOREIGN KEY (`currency_id`)
    REFERENCES `finance_tracker`.`currencies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_expenses_catgories10`
    FOREIGN KEY (`catgory_id`)
    REFERENCES `finance_tracker`.`catgories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_expenses_accounts10`
    FOREIGN KEY (`account_id`)
    REFERENCES `finance_tracker`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_expenses_repeat_types10`
    FOREIGN KEY (`repeat_type_id`)
    REFERENCES `finance_tracker`.`repeat_types` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;