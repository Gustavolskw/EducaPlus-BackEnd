package Educa.plus.Educa.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroUsuarioDTO(

        @NotBlank(message = "Login é obrigatorio!")String login,
        @NotBlank(message = "Senha é obrigatoria!")String senha,
        @NotNull(message = "Nível de Acesso do Usuario é obrigatorio")UserRole role,
        String materia) {
}
