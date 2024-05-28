CREATE TABLE IF NOT EXISTS atividades (
  `id_atividade` VARCHAR(355) NOT NULL,
  `materiax_id` BIGINT NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  `enunciado` VARCHAR(800) NOT NULL,
  `questoes` VARCHAR(200)  NULL,
  `professores_id` BIGINT NOT NULL,
  `resposta_certa` VARCHAR(100) NULL,
  data DATE NOT NULL,
  PRIMARY KEY (`id_atividade`),
  INDEX `materiax_id_idx` (`materiax_id` ASC) VISIBLE,
  INDEX `professores_id_idx` (`professores_id` ASC) VISIBLE,
  CONSTRAINT `materiax_id`
    FOREIGN KEY (`materiax_id`)
    REFERENCES `materias` (`id_materias`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `professores_id`
    FOREIGN KEY (`professores_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
