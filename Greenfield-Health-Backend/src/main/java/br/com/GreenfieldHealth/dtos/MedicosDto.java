package br.com.GreenfieldHealth.dtos;

import br.com.GreenfieldHealth.models.PrescricoesModel;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
