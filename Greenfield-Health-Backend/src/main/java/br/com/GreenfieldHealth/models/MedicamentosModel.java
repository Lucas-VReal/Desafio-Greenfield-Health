package br.com.GreenfieldHealth.models;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
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

    @ManyToOne
    @JoinColumn(name = "prescriptionId")
    private PrescricoesModel prescricao;

}
