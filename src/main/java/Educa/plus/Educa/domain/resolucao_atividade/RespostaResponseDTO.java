package Educa.plus.Educa.domain.resolucao_atividade;

public record RespostaResponseDTO(String idResposta, String atividade, String resposta, String usuario) {
    public RespostaResponseDTO(RespostaAtividade resp){
        this(resp.getIdResposta(), resp.getAtividade().getTitulo(), resp.getRespostaDoAluno(), resp.getAluno().getLogin());
    }
}
