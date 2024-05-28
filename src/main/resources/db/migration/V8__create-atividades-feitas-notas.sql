CREATE TABLE IF NOT EXISTS `atividade_feita_notas` (
  `id_atividade_feita_notas` INT NOT NULL,
  `atividade_feita_id` VARCHAR(355) NOT NULL,
  `nota` DOUBLE NULL,
  `avaliador` BIGINT NOT NULL,
  PRIMARY KEY (`id_atividade_feita_notas`),
  INDEX `atividade_feita_id_idx` (`atividade_feita_id` ASC) VISIBLE,
  INDEX `avaliador` (`avaliador` ASC) VISIBLE,
  CONSTRAINT `atividade_feita_id`
    FOREIGN KEY (`atividade_feita_id`)
    REFERENCES `atividades_feitas` (`id_atividade_feita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `avaliador`
    FOREIGN KEY (`avaliador`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)