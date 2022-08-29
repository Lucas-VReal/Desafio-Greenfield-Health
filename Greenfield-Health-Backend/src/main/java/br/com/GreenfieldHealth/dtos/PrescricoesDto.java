package br.com.GreenfieldHealth.dtos;

import br.com.GreenfieldHealth.models.MedicamentosModel;
import br.com.GreenfieldHealth.models.PacienteModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class PrescricoesDto {

    @NotBlank
    private String description;

    @NotBlank
    List<MedicamentosModel> medicamentos;

    @NotBlank
    private PacienteModel paciente;
}
