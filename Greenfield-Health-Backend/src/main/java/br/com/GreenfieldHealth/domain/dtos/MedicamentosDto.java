package br.com.GreenfieldHealth.domain.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MedicamentosDto {


    @NotBlank
    private String nome;
    @NotBlank
    private String quantidade;
    @NotBlank
    private String dosagem;
    private String frequenciaUso;
}
