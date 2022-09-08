package br.com.GreenfieldHealth.repositories;

import br.com.GreenfieldHealth.domain.models.PacienteModel;
import br.com.GreenfieldHealth.domain.models.PrescricoesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PrescricoesRepository  extends JpaRepository<PrescricoesModel, UUID> {

    boolean existsByPaciente(PacienteModel pacienteReceptor);


    //@Query(value = "delete from PrescricoesModel p where p.prescriptionId = :p", nativeQuery = true)
    @Query(value = "delete from prescricoes where prescription_id = ?1", nativeQuery = true)
    void reallyDeleteById(UUID prescriptionId);
}
