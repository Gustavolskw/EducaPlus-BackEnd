package Educa.plus.Educa.domain.userAnalises;

import Educa.plus.Educa.domain.materia.Materia;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_analise")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserEmAnalise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;
    private String motivo;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
    @JoinColumn(name ="materia_user_analise")
    @ManyToOne
    private Materia materia;


    public UserEmAnalise(String login, String encryptedPassword, String motivo, TipoUsuario tipoUsuario, Materia materia) {
        this.login =login;
        this.senha = encryptedPassword;
        this.motivo = motivo;
        this.tipoUsuario = tipoUsuario;
        if(materia !=null){
            this.materia = materia;
        }
    }
}
