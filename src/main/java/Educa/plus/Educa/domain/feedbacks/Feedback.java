package Educa.plus.Educa.domain.feedbacks;

import Educa.plus.Educa.domain.atividades.Atividades;
import Educa.plus.Educa.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feedbacks_atividades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode()
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id_feedback")
    private String idFeedback;
    @ManyToOne
    @JoinColumn(name = "atividade_id")
    private Atividades atividade;
    private String opiniao;
    @Enumerated(EnumType.STRING)
    private Experiencia experiencia;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;



    public Feedback(Atividades atividade, Usuario user, EnviarFeedbackDTO data){
        this.atividade = atividade;
        this.usuario = user;
        this.opiniao = data.opiniao();
        this.experiencia = data.experiencia();
    }

}

