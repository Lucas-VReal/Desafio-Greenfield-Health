package br.com.GreenfieldHealth.domain.Mappers;

import br.com.GreenfieldHealth.domain.dtos.PacienteDto;
import br.com.GreenfieldHealth.domain.models.PacienteModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PacienteMapper {

    public PacienteMapper INSTANCE = Mappers.getMapper(PacienteMapper.class);

    PacienteDto toDto(PacienteModel entity);
    List<PacienteDto> toDTOS(List<PacienteModel> entities);

    @Mapping(target = "prescricoes", ignore = true)
    PacienteModel toEntity(PacienteDto dto);
}
