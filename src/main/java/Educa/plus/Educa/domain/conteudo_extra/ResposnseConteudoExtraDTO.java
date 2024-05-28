package Educa.plus.Educa.domain.conteudo_extra;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResposnseConteudoExtraDTO(String ContExtraId, String titulo, TipoConteudo tipoConteudo, String Descricao, String videoUrl, String materia, String professor) {

    public ResposnseConteudoExtraDTO(ConteudoExtra cont){
        this(cont.getIdContExtra(), cont.getTitulo(), cont.getTipoConteudo(), cont.getDescricao(), cont.getVideoUrl(), cont.getMateriaId().getMateriaNome(), cont.getProfessorId().getLogin());
    }

}
