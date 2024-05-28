package Educa.plus.Educa.domain.conteudo_extra;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConteudoExtraPostDTO(

        @NotBlank(message = "Titulo deve ser informado")
        String titulo,
        @NotNull(message = "Tipo do Conteudo deve ser informado")
        @JsonAlias({"tipo", "conteudo"})
        TipoConteudo tipoConteudo,
        String descricao,
        String videoUrl,
        @NotBlank(message = "A materia deve ser informada!")
        String materia,
        @NotNull(message = "O Id do Professor deve ser informado")
        Long professor


        ) {
}
