package br.com.GreenfieldHealth.repositories;

import br.com.GreenfieldHealth.domain.models.DoctorsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoctorsRepository extends JpaRepository<DoctorsModel, UUID> {


    boolean existsByCpf(String cpf);

    boolean existsByCrm(String Crm);


}
