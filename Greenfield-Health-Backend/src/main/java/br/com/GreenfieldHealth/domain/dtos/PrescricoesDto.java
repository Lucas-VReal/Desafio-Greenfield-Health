package br.com.GreenfieldHealth.domain.dtos;

import br.com.GreenfieldHealth.domain.models.DoctorsModel;
import br.com.GreenfieldHealth.domain.models.MedicamentosModel;
import br.com.GreenfieldHealth.domain.models.PacienteModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class PrescricoesDto {

    @NotBlank
    private String description;

    private DoctorsModel doctor;

    private List<MedicamentosModel> medicamentos;


    private PacienteModel paciente;

}
