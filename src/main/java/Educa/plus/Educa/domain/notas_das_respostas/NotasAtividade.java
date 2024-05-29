package Educa.plus.Educa.domain.notas_das_respostas;

import Educa.plus.Educa.domain.resolucao_atividade.RespostaAtividade;
import Educa.plus.Educa.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.ToOne;

import java.sql.Blob;

@Entity
@Table(name="atividade_feita_notas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotasAtividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_atividade_feita_notas")
    private Long idNotaAtividade;
    @JoinColumn(name="atividade_feita_id")
    @OneToOne(fetch = FetchType.LAZY)
    private RespostaAtividade respostaAtividade;
    private Double nota;
    @JoinColumn(name = "avaliador")
    @ManyToOne
    private Usuario avaliador;


    public NotasAtividade(RespostaAtividade resposta, Double nota, Usuario avaliador){
        this.respostaAtividade = resposta;
        this.nota = nota;
        this.avaliador = avaliador;
    }
}

