package br.com.GreenfieldHealth.domain.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DoctorDto {
    @NotBlank
    private String cpf;
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    private String dataNascimento;
    private String sexo;
    @NotBlank
    private String crm;
    private boolean estadoCrm;
}
