package Educa.plus.Educa.domain.atividades;

import java.time.LocalDate;

public record DatasDTO(LocalDate data, Integer dia, Integer mes, Integer ano) {
    public DatasDTO(LocalDate atv){
        this(atv, atv.getDayOfMonth(), atv.getMonth().getValue() , atv.getYear());
    }
}
