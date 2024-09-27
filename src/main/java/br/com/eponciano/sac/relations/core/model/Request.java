package br.com.eponciano.sac.relations.core.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class Request {
	private UUID id = UUID.randomUUID();
    @NotBlank(message = "Nome deve ser informado")
    private String nome;
    @NotBlank(message = "Contato deve ser informado")
    private String contato;
    private UUID idAtendente;
    @NotNull(message = "Tipo deve ser informado")
    private SectorEnum tipo;
    @NotBlank(message = "Descricao deve ser informado")
    private String descricao;
    private StatusRequestEnum status = StatusRequestEnum.PENDENTE;
    private LocalDateTime dataCriacao = LocalDateTime.now();

}
