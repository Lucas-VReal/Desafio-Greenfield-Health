package br.com.GreenfieldHealth.repositories;

import br.com.GreenfieldHealth.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RolesRepository extends JpaRepository<RoleModel, UUID> {

    @Query("select roleId from RoleModel where roleName = :role")
    UUID findIdByEnum(@Param("role") Enum roleEnum);

    Optional<RoleModel> findRoleModelByRoleId(UUID roleId);
}
