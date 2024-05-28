package Educa.plus.Educa.domain.resolucao_atividade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record CadastroDeRespostaDTO(
        @NotBlank(message = "Resposta nao deve ser em Branco!")
        String resposta,
        @NotNull(message = "ID do usuario deve ser enviado!")
        Long usuario
) {
}
