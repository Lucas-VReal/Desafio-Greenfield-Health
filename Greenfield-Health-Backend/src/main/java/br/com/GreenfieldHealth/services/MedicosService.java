package br.com.GreenfieldHealth.services;

import br.com.GreenfieldHealth.dtos.MedicosDto;
import br.com.GreenfieldHealth.models.MedicosModel;
import br.com.GreenfieldHealth.models.UsersModel;
import br.com.GreenfieldHealth.repositories.MedicosRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MedicosService {
    private final MedicosRepository medicosRepository;

    public MedicosService(MedicosRepository medicosRepository) {
        this.medicosRepository = medicosRepository;
    }

    public ResponseEntity<Object> findOneMedicById(UUID medicalId) {
        if(medicosRepository.existsById(medicalId)){
            Optional<MedicosModel> optionalMedicosModel = medicosRepository.findById(medicalId);
            MedicosModel medic = optionalMedicosModel.get();
            return ResponseEntity.status(HttpStatus.OK).body(medic);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum médico encontrado com esse Id");
    }

    public ResponseEntity<Object> createANewMedic(MedicosDto medicosDto) {
        //Regra de não cadastrar medicos que com o mesmo CPF
        if(medicosRepository.existsByCpfAndCrm(medicosDto.getCpf(), medicosDto.getCrm())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este CPF/CRM ja esta sendo utilizado");
        }
        //Passando as propriedades validadas para o objeto novoMedico
        if(medicosDto.isEstadoCrm() != true){
            medicosDto.setEstadoCrm(false);
        }
        var novoMedico = new MedicosModel();
        BeanUtils.copyProperties(medicosDto, novoMedico);
        //Salvando no banco PostgreSQL
        medicosRepository.save(novoMedico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMedico);
    }

    public ResponseEntity<Object> updateMedic(UUID id, MedicosDto medicosDto) {
        //Verificar se usuário existe no banco e se o CPF pode ser utilizado
        if(medicosRepository.existsById(id)){
            Optional<MedicosModel> medicosModelOptional = medicosRepository.findById(id);
            MedicosModel updatedMedic = medicosModelOptional.get();
            //Atualizando as informações conforme o JSON
            BeanUtils.copyProperties(medicosDto, updatedMedic);
            //Excluindo antigos dados e salvando novos no banco
            medicosRepository.delete(medicosModelOptional.get());
            medicosRepository.save(updatedMedic);
            return ResponseEntity.status(HttpStatus.OK).body("Medico atualizado e ID modificado");
        }
        //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível atualizar o Médico com os dados informados");
    }


}
