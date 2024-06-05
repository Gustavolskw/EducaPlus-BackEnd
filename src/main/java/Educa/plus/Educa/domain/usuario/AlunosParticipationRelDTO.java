package Educa.plus.Educa.domain.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlunosParticipationRelDTO {
    private String login;
    private Object atividadesFeitas;
    private String materia;
    private Object totalAtividades;
    private Object porcentAtividadeFeitas;
}
