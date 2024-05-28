package Educa.plus.Educa.domain.feedbacks;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnviarFeedbackDTO(
        @JsonAlias({"atividade","id_atividade"})
        @NotBlank(message = "Identificação da atividade necessaria!")
        String atividadeid,
        @NotNull(message = "Experieciencia necessaria!")
        Experiencia experiencia,
        @NotBlank(message = "Opinião necessaria!")
        String opiniao,
        @JsonAlias({"user","usuario"})
        @NotNull(message = "Identificação de Usuario necessaria!")
        Long userId ) {
}
