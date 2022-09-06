package br.com.GreenfieldHealth.domain.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MedicosDto {
    @NotBlank
    private String cpf;
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    private String DataNascimento;
    private String sexo;
    @NotBlank
    private String crm;
    private boolean estadoCrm;
}
