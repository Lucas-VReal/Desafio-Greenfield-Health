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
@Table(name = "medicos")
public class DoctorsModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID doctorsId;
    @Column(unique = true)
    private String cpf;
    private String nome;
    private String email;
    private String DataNascimento;
    private String sexo;
    @Column(unique = true)
    private String crm;
    private boolean estadoCrm;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PrescricoesModel> prescricoes;
}
