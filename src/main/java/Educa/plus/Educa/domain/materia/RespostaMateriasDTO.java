package Educa.plus.Educa.domain.materia;

public record RespostaMateriasDTO(Long materiaId, String materiaName) {
    public RespostaMateriasDTO(Materia materia){
        this(materia.getIdMaterias(), materia.getMateriaNome());
    }
}
