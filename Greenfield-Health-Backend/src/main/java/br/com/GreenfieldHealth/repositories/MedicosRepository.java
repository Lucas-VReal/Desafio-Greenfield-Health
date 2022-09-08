package br.com.GreenfieldHealth.repositories;

import br.com.GreenfieldHealth.domain.models.MedicosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicosRepository extends JpaRepository<MedicosModel, UUID> {


    boolean existsByCpf(String cpf);

    boolean existsByCrm(String Crm);


}
