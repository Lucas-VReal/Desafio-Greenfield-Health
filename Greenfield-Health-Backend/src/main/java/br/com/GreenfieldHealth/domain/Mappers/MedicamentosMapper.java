package br.com.GreenfieldHealth.domain.Mappers;

import br.com.GreenfieldHealth.domain.dtos.MedicamentosDto;
import br.com.GreenfieldHealth.domain.models.MedicamentosModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedicamentosMapper {

    public MedicamentosMapper INSTANCE = Mappers.getMapper(MedicamentosMapper.class);

    MedicamentosDto toDTO(MedicamentosModel medicamento);

    MedicamentosModel toEntity(MedicamentosDto dto);
}
