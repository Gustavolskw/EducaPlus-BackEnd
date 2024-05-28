package Educa.plus.Educa.domain.userAnalises;

import lombok.Getter;

public enum TipoUsuario {
    PROFESSOR("Professor"),
    ALUNO("Aluno");
    private String tipo;

    TipoUsuario(String tipoDeUser ){
        this.tipo = tipoDeUser;
    }
}
