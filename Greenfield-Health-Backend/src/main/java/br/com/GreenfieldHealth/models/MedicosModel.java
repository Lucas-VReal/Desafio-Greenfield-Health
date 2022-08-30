package br.com.GreenfieldHealth.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "medicos")
public class MedicosModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID medicalId;
    @Column(unique = true)
    private String cpf;
    private String nome;
    private String email;
    private String DataNascimento;
    private String sexo;
    @Column(unique = true)
    private String crm;
    private boolean estadoCrm;

    @OneToMany(mappedBy = "prescriptionId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescricoesModel> prescricoes;
}
