package Educa.plus.Educa.domain.materia;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="materias")
@EqualsAndHashCode(of = "id")
public class Materia {
    @Id
    @Column(name="id_materias")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaterias;
    @Column(name="materia_nome")
    private String materiaNome;

    public Materia(String materia) {
        this.materiaNome = materia;
    }
}
