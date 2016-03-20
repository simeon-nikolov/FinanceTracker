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
-- Table `finance_tracker`.`expense_catgories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`expense_catgories` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`expense_tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`expense_tags` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`expenses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`expenses` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `amount` INT(11) NOT NULL,
  `currency_id` INT(11) NOT NULL,
  `account_id` INT(11) NOT NULL,
  `date` DATE NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  `repeat_type_id` INT(11) NOT NULL,
  `expense_catgory_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_expenses_currencies1_idx` (`currency_id` ASC),
  INDEX `fk_expenses_accounts1_idx` (`account_id` ASC),
  INDEX `fk_expenses_repeat_types1_idx` (`repeat_type_id` ASC),
  INDEX `fk_expenses_expense_catgories1_idx` (`expense_catgory_id` ASC),
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
    FOREIGN KEY (`expense_catgory_id`)
    REFERENCES `finance_tracker`.`expense_catgories` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_expenses_repeat_types1`
    FOREIGN KEY (`repeat_type_id`)
    REFERENCES `finance_tracker`.`repeat_types` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`expenses_has_expense_tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`expenses_has_expense_tags` (
  `expense_id` INT(11) NOT NULL,
  `expense_tag_id` INT(11) NOT NULL,
  INDEX `fk_expenses_has_tags_expenses1_idx` (`expense_id` ASC),
  INDEX `fk_expenses_has_tags_expense_tags1_idx` (`expense_tag_id` ASC),
  CONSTRAINT `fk_expenses_has_tags_expense_tags1`
    FOREIGN KEY (`expense_tag_id`)
    REFERENCES `finance_tracker`.`expense_tags` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_expenses_has_tags_expenses1`
    FOREIGN KEY (`expense_id`)
    REFERENCES `finance_tracker`.`expenses` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`income_catgories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`income_catgories` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`income_tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`income_tags` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`incomes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`incomes` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `amount` INT(11) NOT NULL,
  `currency_id` INT(11) NOT NULL,
  `account_id` INT(11) NOT NULL,
  `date` DATE NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  `repeat_type_id` INT(11) NOT NULL,
  `income_catgory_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_expenses_currencies1_idx` (`currency_id` ASC),
  INDEX `fk_expenses_accounts1_idx` (`account_id` ASC),
  INDEX `fk_expenses_repeat_types1_idx` (`repeat_type_id` ASC),
  INDEX `fk_incomes_income_catgories1_idx` (`income_catgory_id` ASC),
  CONSTRAINT `fk_expenses_accounts10`
    FOREIGN KEY (`account_id`)
    REFERENCES `finance_tracker`.`accounts` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_expenses_currencies10`
    FOREIGN KEY (`currency_id`)
    REFERENCES `finance_tracker`.`currencies` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_expenses_repeat_types10`
    FOREIGN KEY (`repeat_type_id`)
    REFERENCES `finance_tracker`.`repeat_types` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_incomes_income_catgories1`
    FOREIGN KEY (`income_catgory_id`)
    REFERENCES `finance_tracker`.`income_catgories` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `finance_tracker`.`income_has_income_tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finance_tracker`.`income_has_income_tags` (
  `income_tag_id` INT(11) NOT NULL,
  `income_id` INT(11) NOT NULL,
  INDEX `fk_income_tags_has_incomes_incomes1_idx` (`income_id` ASC),
  INDEX `fk_income_tags_has_incomes_income_tags1_idx` (`income_tag_id` ASC),
  CONSTRAINT `fk_income_tags_has_incomes_income_tags1`
    FOREIGN KEY (`income_tag_id`)
    REFERENCES `finance_tracker`.`income_tags` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_income_tags_has_incomes_incomes1`
    FOREIGN KEY (`income_id`)
    REFERENCES `finance_tracker`.`incomes` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
