ALTER TABLE `users`
ADD COLUMN `ativo` TINYINT NOT NULL AFTER `materias_id`;


ALTER TABLE `atividade_feita_notas`
CHANGE COLUMN `id_atividade_feita_notas` `id_atividade_feita_notas` INT NOT NULL AUTO_INCREMENT ;
