package Educa.plus.Educa.domain.resolucao_atividade;

import Educa.plus.Educa.domain.atividades.Atividades;
import Educa.plus.Educa.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="atividades_feitas")
@Setter@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RespostaAtividade {
    @Id
    @Column(name = "id_atividade_feita")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idResposta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atividades_id")
    private Atividades atividade;
    @Column(name = "resposta")
    private String respostaDoAluno;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "alunos_id")
    private Usuario aluno;


    public RespostaAtividade(Atividades atividade, String resposta, Usuario aluno){
        this.atividade = atividade;
        this.respostaDoAluno = resposta;
        this.aluno = aluno;
    }


}
