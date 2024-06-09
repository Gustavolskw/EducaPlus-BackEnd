package Educa.plus.Educa.domain.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public  class RelatorioPostProfessores {
    private String professor;
    private Object qtdPosts;
    private String materia;
    private Object porcentagemPost;
    private Object totalAtividades;
}
