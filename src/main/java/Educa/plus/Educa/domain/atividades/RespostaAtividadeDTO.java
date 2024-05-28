package Educa.plus.Educa.domain.atividades;

import java.time.LocalDate;

public record RespostaAtividadeDTO(String idAtividade, String titulo, Integer tipoAtividade,  String enunciado, String questoes, String resposta,  String professor, String materia, LocalDate data) {
    public RespostaAtividadeDTO(Atividades atv){
        this(atv.getIdAtividade(), atv.getTitulo(), atv.getTipoAtividade().ordinal(), atv.getEnunciado(), atv.getQuestoes(), atv.getRespostaCerta(), atv.getProfessor().getLogin(), atv.getMateria().getMateriaNome(), atv.getData());
    }
}
