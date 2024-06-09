package Educa.plus.Educa.domain.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioDeNotasDTO {
    private String login;
    private String materia;
    private Object media;
    private Object maiorNota;
    private Object menorNota;
    private Object porcentAcima;
}

