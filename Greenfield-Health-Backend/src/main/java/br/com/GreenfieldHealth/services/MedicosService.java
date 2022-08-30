package br.com.GreenfieldHealth.services;

import br.com.GreenfieldHealth.dtos.MedicosDto;
import br.com.GreenfieldHealth.models.MedicosModel;
import br.com.GreenfieldHealth.models.PrescricoesModel;
import br.com.GreenfieldHealth.repositories.MedicosRepository;
import br.com.GreenfieldHealth.repositories.PrescricoesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicosService {
    private final MedicosRepository medicosRepository;
    private final PrescricoesRepository prescricoesRepository;

    public MedicosService(MedicosRepository medicosRepository, PrescricoesRepository prescricoesRepository) {
        this.medicosRepository = medicosRepository;
        this.prescricoesRepository = prescricoesRepository;
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
        //Verificar se usuário existe no banco
        if(medicosRepository.existsById(id)){
            Optional<MedicosModel> medicosModelOptional = medicosRepository.findById(id);
            MedicosModel updatedMedic = medicosModelOptional.get();
            //Atualizando as informações conforme o JSON
            BeanUtils.copyProperties(medicosDto, updatedMedic);
            //Salvando novos dados no banco
            medicosRepository.save(updatedMedic);
            medicosRepository.flush();
            return ResponseEntity.status(HttpStatus.OK).body(updatedMedic);
        }
        //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível atualizar o Médico com os dados informados");
    }


    public ResponseEntity<Object> deleteMerdicById(UUID id) {
        if(medicosRepository.existsById(id)){
            Optional<MedicosModel> medicosModelOptional = medicosRepository.findById(id);
            MedicosModel deletedMedic = medicosModelOptional.get();
            //Removendo medico
            medicosRepository.delete(deletedMedic);
            medicosRepository.flush();
            return ResponseEntity.status(HttpStatus.OK).body("Médico deletado com sucesso!");
        }
        //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível remover o Médico com os dados informados");

    }
}
