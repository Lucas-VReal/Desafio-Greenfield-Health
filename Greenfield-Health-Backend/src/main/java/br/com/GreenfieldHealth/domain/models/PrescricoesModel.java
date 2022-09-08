package br.com.GreenfieldHealth.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "prescricoes")
public class PrescricoesModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID prescriptionId;
    private String description;

    @ManyToOne
    @JoinColumn(name = "medicos_id")
    private MedicosModel medico;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "medicamento_id")
    private List<MedicamentosModel> medicamentos;

    @OneToOne
    @JoinColumn(name = "paciente_id")
    private PacienteModel paciente;

}
