package Educa.plus.Educa.domain.notas_das_respostas;

public record RespostaNotaDTO(Long idNota, String titulo, String enunciado, String questoes, String respostaDoAluno, String respostaCerta, String aluno, String professor, Double nota
) {
    public RespostaNotaDTO(NotasAtividade nota){
        this(
                nota.getIdNotaAtividade(),
                nota.getRespostaAtividade().getAtividade().getTitulo(),
                nota.getRespostaAtividade().getAtividade().getEnunciado(),
                nota.getRespostaAtividade().getAtividade().getQuestoes(),
                nota.getRespostaAtividade().getRespostaDoAluno(),
                nota.getRespostaAtividade().getAtividade().getRespostaCerta(),
                nota.getRespostaAtividade().getAluno().getLogin(),
                nota.getAvaliador().getLogin(),
                nota.getNota());
    }


}
