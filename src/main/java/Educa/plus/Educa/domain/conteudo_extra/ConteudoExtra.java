package Educa.plus.Educa.domain.conteudo_extra;

import Educa.plus.Educa.domain.materia.Materia;
import Educa.plus.Educa.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "conteudo_extra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ConteudoExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id_conteudo_extra")
    private String idContExtra;
    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materiaId;
    private String titulo;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoConteudo tipoConteudo;
    private  String descricao;
    @Column(name = "video_url")
    private String videoUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Usuario professorId;

    public ConteudoExtra(Materia materia, String titulo, TipoConteudo tipoConteudo, String descricao, String videoUrl, Usuario professor){
        this.materiaId = materia;
        this.titulo = titulo;
        this.tipoConteudo = tipoConteudo;
        this.descricao = descricao;
        this.videoUrl = videoUrl;
        this.professorId = professor;
    }

    public void updateContExtra(String titulo,String descricao, String videoUrl){
        this.titulo = titulo;
        this.descricao = descricao;
        this.videoUrl = videoUrl;
    }




}
