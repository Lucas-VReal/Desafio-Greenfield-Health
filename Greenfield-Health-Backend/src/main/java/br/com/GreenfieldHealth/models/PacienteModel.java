package br.com.GreenfieldHealth.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "paciente")
public class PacienteModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pacienteId;
    @Column (unique = true)
    private String cpf;
    @Column
    private String nome;
    @Column
    private String dataNascimento;
    @OneToOne(mappedBy = "paciente")
    private PrescricoesModel prescricao;
}
