CREATE TABLE IF NOT EXISTS atividades_feitas (
  `id_atividade_feita` VARCHAR(355) NOT NULL,
  `atividades_id` VARCHAR(355) NOT NULL,
  `resposta` VARCHAR(200) NULL,
  `alunos_id` BIGINT NOT NULL,
  PRIMARY KEY (`id_atividade_feita`),
  INDEX `atividades_id_idx` (`atividades_id` ASC) VISIBLE,
  INDEX `alunos_id_idx` (`alunos_id` ASC) VISIBLE,
  CONSTRAINT `atividades_id`
    FOREIGN KEY (`atividades_id`)
    REFERENCES `atividades` (`id_atividade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `alunos_id`
    FOREIGN KEY (`alunos_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)