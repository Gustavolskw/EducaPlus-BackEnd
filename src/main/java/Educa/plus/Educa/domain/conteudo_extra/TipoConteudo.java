package Educa.plus.Educa.domain.conteudo_extra;

import lombok.Getter;

@Getter
public enum TipoConteudo {
    VIDEO("Video"),
    TEXTO("Texto");

    private String tipo;

    TipoConteudo(String type){
        this.tipo = type;
    }

}
