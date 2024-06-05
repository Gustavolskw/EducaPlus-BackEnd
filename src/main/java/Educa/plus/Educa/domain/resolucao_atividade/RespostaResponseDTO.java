package Educa.plus.Educa.domain.resolucao_atividade;

public record RespostaResponseDTO(String idResposta, String atividade,String tipoDeAtividade, String materia, String enunciado, String questoes,  String resposta,String respostaCerta, String usuario) {
    public RespostaResponseDTO(RespostaAtividade resp){
        this(resp.getIdResposta(), resp.getAtividade().getTitulo(), resp.getAtividade().getTipoAtividade().name(), resp.getAtividade().getMateria().getMateriaNome(), resp.getAtividade().getEnunciado(),resp.getAtividade().getQuestoes(), resp.getRespostaDoAluno(), resp.getAtividade().getRespostaCerta(), resp.getAluno().getLogin());
    }
}
