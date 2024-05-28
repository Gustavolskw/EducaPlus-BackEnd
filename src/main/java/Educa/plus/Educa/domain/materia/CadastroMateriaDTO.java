package Educa.plus.Educa.domain.materia;

import jakarta.validation.constraints.NotNull;

public record CadastroMateriaDTO(

        @NotNull(message = "Nome da materia deve ser informado!") String materia) {
}
