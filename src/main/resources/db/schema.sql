-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8mb4 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table USER
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS USER (
  id BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  UNIQUE INDEX username_UNIQUE (username ASC),
  UNIQUE INDEX email_UNIQUE (email ASC),
  PRIMARY KEY (id)
  )ENGINE = InnoDB;
  
-- -----------------------------------------------------
-- Table TRANSACTION
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS TRANSACTION (
  id BIGINT NOT NULL AUTO_INCREMENT,
  sender_id BIGINT NOT NULL,
  receiver_id BIGINT NOT NULL,
  description VARCHAR(255),
  amount DOUBLE NOT NULL,
  date DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX fk_transactions_sender_idx (sender_id ASC),
  INDEX fk_transactions_receiver_idx (receiver_id ASC),
  CONSTRAINT fk_transactions_sender
    FOREIGN KEY (sender_id)
    REFERENCES USER (id)
    ON UPDATE NO ACTION,
  CONSTRAINT fk_transactions_receiver
    FOREIGN KEY (receiver_id)
    REFERENCES USER (id)
    ON UPDATE NO ACTION
	)ENGINE = InnoDB;
	
-- -----------------------------------------------------
-- Table CONNECTION
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS CONNECTION (
  user_id BIGINT NOT NULL,
  connection_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, connection_id),
  INDEX fk_connection_user_idx (user_id ASC),
  INDEX fk_connection_connection_idx (connection_id ASC),
  CONSTRAINT fk_connection_user
    FOREIGN KEY (user_id)
    REFERENCES USER (id)
    ON UPDATE NO ACTION,
  CONSTRAINT fk_connection_connection
    FOREIGN KEY (connection_id)
    REFERENCES USER (id)
    ON UPDATE NO ACTION
	)ENGINE = InnoDB;	