package br.com.GreenfieldHealth.domain.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "medicamentos")
public class MedicamentosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID medicamentosId;
    @Column
    private String nome;
    @Column
    private String quantidade;
    @Column
    private String dosagem;
    @Column
    private String frequenciaUso;

}
