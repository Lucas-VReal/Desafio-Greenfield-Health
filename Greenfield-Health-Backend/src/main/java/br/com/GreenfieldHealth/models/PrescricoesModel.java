package br.com.GreenfieldHealth.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "prescricoes")
public class PrescricoesModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID prescriptionId;
    private String description;

    @ManyToOne
    @JoinColumn(name = "medical_id")
    private MedicosModel medico;

    @OneToMany(mappedBy = "medicamentosId", cascade = CascadeType.ALL)
    private List<MedicamentosModel> medicamentos;

    @OneToOne
    @JoinColumn(name = "paciente_id")
    private PacienteModel paciente;

}
