package br.com.eponciano.sac.relations.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SectorEnum {

    CARD(1),
    CREDIT(2),
    OTHERS(3);

    private final int cod;

}
