package Educa.plus.Educa.domain.notas_das_respostas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostaNotaDTO(

        @NotNull(message = "Nota da tarefa deve ser informada!")
        Double nota,
        @NotNull(message = "ID do avaliador da tarefa deve ser informada!")
        Long avaliador
) {
}
