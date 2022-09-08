package br.com.GreenfieldHealth.domain.Mappers;

import br.com.GreenfieldHealth.domain.dtos.PrescricoesDto;
import br.com.GreenfieldHealth.domain.models.PrescricoesModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = { MedicamentosMapper.class })
public interface PrescricoesMapper {

    public PrescricoesMapper INSTANCE = Mappers.getMapper(PrescricoesMapper.class);

    PrescricoesDto toDTO(PrescricoesModel entity);
    List<PrescricoesDto> toDTOs(List<PrescricoesModel> entities);

    PrescricoesModel toEntity(PrescricoesDto dto);

}
