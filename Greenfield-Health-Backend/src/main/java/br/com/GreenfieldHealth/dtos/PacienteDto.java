package br.com.GreenfieldHealth.dtos;

import br.com.GreenfieldHealth.models.PrescricoesModel;
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
