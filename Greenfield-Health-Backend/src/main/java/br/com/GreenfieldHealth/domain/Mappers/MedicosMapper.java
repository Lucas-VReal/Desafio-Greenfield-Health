package br.com.GreenfieldHealth.domain.Mappers;

import br.com.GreenfieldHealth.domain.dtos.DoctorDto;
import br.com.GreenfieldHealth.domain.models.DoctorsModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MedicosMapper {

    public MedicosMapper INSTANCE = Mappers.getMapper(MedicosMapper.class);

    DoctorDto toDTO(DoctorsModel entity);
    List<DoctorDto> toDTOs(List<DoctorsModel> entities);

    DoctorsModel toEntity(DoctorDto dto);
}
