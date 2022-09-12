package br.com.GreenfieldHealth.domain.services;

import br.com.GreenfieldHealth.domain.models.DoctorsModel;
import br.com.GreenfieldHealth.domain.models.PrescricoesModel;
import br.com.GreenfieldHealth.repositories.DoctorsRepository;
import br.com.GreenfieldHealth.repositories.PrescricoesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorService {
    private final DoctorsRepository doctorsRepository;
    private final PrescricoesRepository prescricoesRepository;

    public DoctorService(DoctorsRepository doctorsRepository, PrescricoesRepository prescricoesRepository) {
        this.doctorsRepository = doctorsRepository;
        this.prescricoesRepository = prescricoesRepository;
    }


    public ResponseEntity<Object> findOneDoctorById(UUID DoctorId) {
        if(doctorsRepository.existsById(DoctorId)){
            Optional<DoctorsModel> optionalDoctorsModel = doctorsRepository.findById(DoctorId);
            DoctorsModel Doctor = optionalDoctorsModel.get();
            return ResponseEntity.status(HttpStatus.OK).body(Doctor);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum médico encontrado com esse Id");
    }

    public ResponseEntity<Object> createANewDoctor(DoctorsModel newDoctor) {
        //Regra de não cadastrar Doctoros que com o mesmo CPF
        if(doctorsRepository.existsByCpf(newDoctor.getCpf()) || doctorsRepository.existsByCrm(newDoctor.getCrm())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este CPF/CRM ja esta sendo utilizado");
        }else {
            //Salvando no banco PostgreSQL
            doctorsRepository.save(newDoctor);
            return ResponseEntity.status(HttpStatus.CREATED).body(newDoctor);
        }

    }

    public ResponseEntity<Object> updateDoctor(UUID id, DoctorsModel updatedDoctor) {
        //Verificar se usuário existe no banco
        if(doctorsRepository.existsById(id)){
            //Salvando novos dados no banco
            updatedDoctor.setDoctorsId(id);
            doctorsRepository.save(updatedDoctor);
            doctorsRepository.flush();
            return ResponseEntity.status(HttpStatus.OK).body(updatedDoctor);
        }
        //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível atualizar o Médico com os dados informados");
    }


    public ResponseEntity<Object> deleteDoctorById(UUID id) {
        if(doctorsRepository.existsById(id)){
            Optional<DoctorsModel> DoctorsModelOptional = doctorsRepository.findById(id);
            DoctorsModel deletedDoctor = DoctorsModelOptional.get();
            //Gambiarra
            for(int i = 0; i < deletedDoctor.getPrescricoes().size(); i++){
                PrescricoesModel prescription = deletedDoctor.getPrescricoes().get(i);
                prescription.setDoctor(null);
                prescricoesRepository.save(prescription);
            }
            //Removendo Médico
            doctorsRepository.deleteById(id);
            doctorsRepository.flush();
            return ResponseEntity.status(HttpStatus.OK).body("Médico deletado com sucesso!");
        }
        //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível remover o Médico com os dados informados");

    }
}
