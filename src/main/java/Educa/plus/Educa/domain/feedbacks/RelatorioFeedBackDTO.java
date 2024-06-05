package Educa.plus.Educa.domain.feedbacks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RelatorioFeedBackDTO {
    // Getters e Setters
    private String materia;
    private Object feedbacks;
    private Object neutras;
    private Object positivas;
    private Object negativas;
    private Object porcentPos;

    // Construtor vazio (necessário para JPA)
    public RelatorioFeedBackDTO() {
    }

}