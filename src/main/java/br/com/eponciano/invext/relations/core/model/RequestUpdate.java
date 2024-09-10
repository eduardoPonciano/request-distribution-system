package br.com.eponciano.invext.relations.core.model;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdate {
	@NotNull
    private UUID idAtendente;
    @NotNull(message = "Tipo deve ser informado")
    private SectorEnum tipo;
    @NotNull(message = "status deve ser informado")
    private StatusRequestEnum status;

}
