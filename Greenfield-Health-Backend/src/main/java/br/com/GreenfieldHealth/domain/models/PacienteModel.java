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
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PrescricoesModel> prescricoes;
}
