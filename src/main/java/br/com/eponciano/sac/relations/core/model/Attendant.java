package br.com.eponciano.sac.relations.core.model;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attendant {

	private UUID id = UUID.randomUUID();

    @NotBlank(message = "Nome deve ser informado")
	private String nome; 
    @NotBlank(message = "Setor deve ser informado")
	private SectorEnum setor;
}
