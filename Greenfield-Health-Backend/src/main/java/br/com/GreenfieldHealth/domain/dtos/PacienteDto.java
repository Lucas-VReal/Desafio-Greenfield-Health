package br.com.GreenfieldHealth.domain.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class PacienteDto {
    @NotBlank
    private String cpf;
    @NotBlank
    private String nome;
    @NotBlank
    private String dataNascimento;

}
