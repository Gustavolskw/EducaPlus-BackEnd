package Educa.plus.Educa.domain.atividades;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CadastroAtividadeDTO(
        @NotBlank(message = "O titulo da atividade deve ser informado!")
        String titulo,
        @NotNull(message = "Deve ser Informado o tipo da atividade!")
        TipoAtividade tipoAtividade,

        String enunciado,

        String questoes,

        String resposta,

        @NotNull(message = "A Identificação do Professor deve ser informada!")
        Long professor,
        @NotBlank(message = "O Nome da Materia deve ser informada!")
        String materia
       ) {
}
