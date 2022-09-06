package br.com.GreenfieldHealth.repositories;

import br.com.GreenfieldHealth.domain.models.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PacienteRepository  extends JpaRepository<PacienteModel, UUID> {
    boolean existsByCpf(String cpf);
}
