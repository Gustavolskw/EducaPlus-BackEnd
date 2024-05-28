package Educa.plus.Educa.domain.atividades;

import Educa.plus.Educa.domain.materia.Materia;
import Educa.plus.Educa.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "atividades")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Atividades {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_atividade")
    private String idAtividade;
    @ManyToOne
    @JoinColumn(name = "materiax_id")
    private Materia materia;
    private String titulo;
    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoAtividade tipoAtividade;
    private String enunciado;
    private String questoes;
    @ManyToOne
    @JoinColumn(name = "professores_id")
    private Usuario professor;
    @Column(name = "resposta_certa")
    private String respostaCerta;
    private LocalDate data;


    public Atividades(String titulo, TipoAtividade tipoAtividade, String enunciado, String questoes, String resposta, Usuario professor, Materia materia){
        this.titulo = titulo;
        this.tipoAtividade = tipoAtividade;
        this.enunciado = enunciado;
        this.questoes = questoes;
        this.respostaCerta = resposta;
        this.professor = professor;
        this.materia = materia;
        this.data = LocalDate.now();
    }

}
