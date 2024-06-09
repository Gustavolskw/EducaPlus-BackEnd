package Educa.plus.Educa.domain.atividades;

import jakarta.validation.constraints.NotNull;

public record DatasRecebeDTO(@NotNull(message = "Dia Inicial da busca deve ser Informado!")
                             Integer diaInit,
                             @NotNull(message = "Dia Final da busca deve ser Informado!")
                             Integer diaFim,
                             @NotNull(message = "Mês da busca deve ser Informado!")
                             Integer mesInicial,
                             @NotNull(message = "Mês da busca deve ser Informado!")
                             Integer mesFinal,
                             @NotNull(message = "Ano da busca deve ser Informado!")
                             Integer anoBusca) {
}
