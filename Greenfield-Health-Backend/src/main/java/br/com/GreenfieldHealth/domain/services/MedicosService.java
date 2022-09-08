package br.com.GreenfieldHealth.domain.services;

import br.com.GreenfieldHealth.domain.dtos.MedicosDto;
import br.com.GreenfieldHealth.domain.models.MedicosModel;
import br.com.GreenfieldHealth.domain.models.PrescricoesModel;
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

    public ResponseEntity<Object> createANewMedic(MedicosModel newMedic) {
        //Regra de não cadastrar medicos que com o mesmo CPF
        if(medicosRepository.existsByCpf(newMedic.getCpf()) || medicosRepository.existsByCrm(newMedic.getCrm())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este CPF/CRM ja esta sendo utilizado");
        }else {
            //Salvando no banco PostgreSQL
            medicosRepository.save(newMedic);
            return ResponseEntity.status(HttpStatus.CREATED).body(newMedic);
        }

    }

    public ResponseEntity<Object> updateMedic(UUID id, MedicosModel updatedMedic) {
        //Verificar se usuário existe no banco
        if(medicosRepository.existsById(id)){
            //Salvando novos dados no banco
            updatedMedic.setMedicalId(id);
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
            //Gambiarra
            for(int i = 0; i < deletedMedic.getPrescricoes().size(); i++){
                PrescricoesModel prescription = deletedMedic.getPrescricoes().get(i);
                prescription.setMedico(null);
                prescricoesRepository.save(prescription);
            }
            //Removendo medico
            medicosRepository.deleteById(id);
            medicosRepository.flush();
            return ResponseEntity.status(HttpStatus.OK).body("Médico deletado com sucesso!");
        }
        //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível remover o Médico com os dados informados");

    }
}
