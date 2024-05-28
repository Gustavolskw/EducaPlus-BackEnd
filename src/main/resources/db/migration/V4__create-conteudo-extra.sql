CREATE TABLE IF NOT EXISTS conteudo_extra (
  `id_conteudo_extra` VARCHAR(355) NOT NULL,
  `materia_id` BIGINT NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  `descricao` VARCHAR(200) NULL,
  `video_url` VARCHAR(355) NULL,
  `professor_id` BIGINT NOT NULL,
  PRIMARY KEY (`id_conteudo_extra`),
  INDEX `materia_id_idx` (`materia_id` ASC) VISIBLE,
  INDEX `professor_id_idx` (`professor_id` ASC) VISIBLE,
  CONSTRAINT `materia_id`
    FOREIGN KEY (`materia_id`)
    REFERENCES `materias` (`id_materias`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `professor_id`
    FOREIGN KEY (`professor_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
