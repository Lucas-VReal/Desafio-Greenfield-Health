package br.com.GreenfieldHealth.domain.Mappers;

import br.com.GreenfieldHealth.domain.dtos.MedicosDto;
import br.com.GreenfieldHealth.domain.models.MedicosModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MedicosMapper {

    public MedicosMapper INSTANCE = Mappers.getMapper(MedicosMapper.class);

    MedicosDto toDTO(MedicosModel entity);
    List<MedicosDto> toDTOs(List<MedicosModel> entities);

    MedicosModel toEntity(MedicosDto dto);
}
