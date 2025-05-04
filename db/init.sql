SET GLOBAL wait_timeout = 2147483;
SET GLOBAL interactive_timeout = 2147483;


CREATE DATABASE IF NOT EXISTS `botdata`;
USE botdata;

CREATE TABLE IF NOT EXISTS `banned_words`
(
    `id`   INT      NOT NULL AUTO_INCREMENT,
    `word` TINYTEXT NOT NULL,
    PRIMARY KEY (`id`)

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `flags`
(
    `id`    INT      NOT NULL AUTO_INCREMENT,
    `flag`  TINYTEXT NOT NULL,
    `state` TINYINT(1) DEFAULT NULL,
    PRIMARY KEY (`id`)

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `stock`
(
    `id`          INT      NOT NULL AUTO_INCREMENT,
    `name`        TINYTEXT NOT NULL,
    `amount`      INT      NOT NULL,
    `auto_stock`  TINYINT(1) DEFAULT NULL,
    `interval`    INT      NOT NULL,
    `auto_amount` INT      NOT NULL,
    PRIMARY KEY (`id`)

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

