package Educa.plus.Educa.domain.userAnalises;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroUsuarioEmAnaliseDTO(
        @NotBlank(message = "Login é obrigatorio!")String login,
        @NotBlank(message = "Senha é obrigatoria!")String senha,
        @NotBlank(message = "necessario informar o motivo do seu Cadastro!")String motivo,
        @NotNull(message = "é necessario informar qual o tipo de usuario!")TipoUsuario tipoUsuario,
        String materia) {
}
