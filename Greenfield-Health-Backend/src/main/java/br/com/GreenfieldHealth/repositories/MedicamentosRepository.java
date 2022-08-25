package br.com.GreenfieldHealth.repositories;

import br.com.GreenfieldHealth.models.MedicamentosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicamentosRepository  extends JpaRepository<MedicamentosModel, UUID> {
}
