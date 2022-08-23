package br.com.GreenfieldHealth.repositories;

import br.com.GreenfieldHealth.models.UsuariosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosModel, Long> {

    public Optional<UsuariosModel> findUsuariosModelByLogin(String login);

    public boolean existsByLogin(String login);
}
