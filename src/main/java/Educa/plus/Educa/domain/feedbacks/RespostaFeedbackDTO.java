package Educa.plus.Educa.domain.feedbacks;

public record RespostaFeedbackDTO(String idFeedback, String opiniao, Experiencia experiencia, String usuario) {
    public RespostaFeedbackDTO(Feedback feed){
        this(feed.getIdFeedback(), feed.getOpiniao(), feed.getExperiencia(), feed.getUsuario().getLogin());
    }


}
