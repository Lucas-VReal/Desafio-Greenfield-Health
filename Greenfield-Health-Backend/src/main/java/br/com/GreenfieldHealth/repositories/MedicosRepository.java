package br.com.GreenfieldHealth.repositories;

import br.com.GreenfieldHealth.models.MedicosModel;
import br.com.GreenfieldHealth.models.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicosRepository extends JpaRepository<MedicosModel, UUID> {

    boolean existsByCpfAndCrm(String cpf, String Crm);


}
