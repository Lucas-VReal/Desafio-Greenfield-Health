package br.com.GreenfieldHealth.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "medicos")
public class MedicosModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Medicalid;
    @Column(unique = true)
    private String cpf;
    @Column
    private String nome;
    @Column
    private String email;
    @Column
    private String DataNascimento;
    @Column
    private String sexo;
    @Column(unique = true)
    private String crm;
    @Column
    private boolean estadoCrm;

}
