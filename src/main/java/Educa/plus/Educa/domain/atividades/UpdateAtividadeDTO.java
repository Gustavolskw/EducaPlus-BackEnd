package Educa.plus.Educa.domain.atividades;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateAtividadeDTO(
        @NotBlank(message = "Deve ser informado a identificação da atividade!")
        @JsonAlias({"atividade", "id"})
        String idAtividade,
        String titulo,
        String enunciado,
        String questoes,
        String resposta) {
}
