CREATE TABLE IF NOT EXISTS users (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `senha` VARCHAR(355) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  `materias_id` BIGINT NULL,
  PRIMARY KEY (`id`),
  INDEX `materias_id_idx` (`materias_id` ASC) VISIBLE,
  CONSTRAINT `materias_id`
    FOREIGN KEY (`materias_id`)
    REFERENCES `materias` (`id_materias`)
   )
