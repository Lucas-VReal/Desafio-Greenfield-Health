package br.com.GreenfieldHealth.repositories;

import br.com.GreenfieldHealth.domain.models.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<UsersModel, UUID> {

    Optional<UsersModel> findUsuariosModelByUsername(String username);

    boolean existsByUsername(String username);


    Optional<UsersModel> findByUsername(String username);
}
