package br.com.eponciano.sac.relations.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusRequestEnum {

    PENDENTE(1),
    EM_ANALISE(2),
    RESOLVIDO(3),
    REJEITADO(4);

    private final int cod;

}
