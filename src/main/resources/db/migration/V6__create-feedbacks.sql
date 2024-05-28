CREATE TABLE IF NOT EXISTS `feedbacks_atividades` (
  `id_feedback` VARCHAR(355) NOT NULL,
  `atividade_id` VARCHAR(355) NOT NULL,
  `opiniao` VARCHAR(300) NOT NULL,
  `experiencia` VARCHAR(45) NOT NULL,
  `usuario_id` BIGINT NOT NULL,
  PRIMARY KEY (`id_feedback`),
  INDEX `atividade_id_idx` (`atividade_id` ASC) VISIBLE,
  INDEX `usuario_id_idx` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `atividade_id`
    FOREIGN KEY (`atividade_id`)
    REFERENCES `atividades` (`id_atividade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `usuario_id`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
