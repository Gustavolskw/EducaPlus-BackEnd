CREATE TABLE IF NOT EXISTS `user_analise` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
    `login` VARCHAR(45) NOT NULL,
    `senha` VARCHAR(355) NOT NULL,
    `motivo` VARCHAR(200) NOT NULL,
    `tipo_usuario` VARCHAR(45) NOT NULL,
    `materia_user_analise` BIGINT NULL,
    PRIMARY KEY (`id`),
    INDEX `materia_user_analise_idx` (`materia_user_analise` ASC) INVISIBLE,
    CONSTRAINT `materia_user_analise`
      FOREIGN KEY (`materia_user_analise`)
      REFERENCES `materias` (`id_materias`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION)