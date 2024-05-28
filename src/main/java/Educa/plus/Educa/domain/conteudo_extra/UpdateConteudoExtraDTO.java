package Educa.plus.Educa.domain.conteudo_extra;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record UpdateConteudoExtraDTO(
        @NotBlank(message = "Id do Conteudo deve ser informado")
        @JsonAlias({"id", "conteudo"})
        String ConteudoId,
        String titulo,
        String videoUrl,
        String descricao) {
}
