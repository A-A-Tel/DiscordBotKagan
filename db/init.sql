SET GLOBAL wait_timeout = 2147483;
SET GLOBAL interactive_timeout = 2147483;

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
    `id`    INT NOT NULL AUTO_INCREMENT,
    `flag`  TINYTEXT,
    `state` TINYINT(1) DEFAULT NULL,
    PRIMARY KEY (`id`)

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;