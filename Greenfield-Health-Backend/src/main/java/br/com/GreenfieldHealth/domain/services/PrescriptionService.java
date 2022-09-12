package br.com.GreenfieldHealth.domain.services;


import br.com.GreenfieldHealth.domain.Mappers.PrescricoesMapper;
import br.com.GreenfieldHealth.domain.dtos.PrescricoesDto;
import br.com.GreenfieldHealth.domain.models.DoctorsModel;
import br.com.GreenfieldHealth.domain.models.PacienteModel;
import br.com.GreenfieldHealth.domain.models.PrescricoesModel;
import br.com.GreenfieldHealth.repositories.MedicamentosRepository;
import br.com.GreenfieldHealth.repositories.DoctorsRepository;
import br.com.GreenfieldHealth.repositories.PacienteRepository;
import br.com.GreenfieldHealth.repositories.PrescricoesRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class PrescriptionService {

    private final PrescricoesRepository prescricoesRepository;
    private final DoctorsRepository doctorsRepository;
    private final PacienteRepository pacienteRepository;

    private final MedicamentosRepository medicamentosRepository;

    public PrescriptionService(PrescricoesRepository prescricoesRepository, DoctorsRepository doctorsRepository, PacienteRepository pacienteRepository, MedicamentosRepository medicamentosRepository) {
        this.prescricoesRepository = prescricoesRepository;
        this.doctorsRepository = doctorsRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicamentosRepository = medicamentosRepository;
    }

    public ResponseEntity<Object> findAllPrescriptionsByMedicId(UUID medicalId) {
        if(doctorsRepository.existsById(medicalId)){
            Optional<DoctorsModel> medicosModelOptional  = doctorsRepository.findById(medicalId);
            DoctorsModel medico = medicosModelOptional.get();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(medico.getPrescricoes());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado");
    }
    public ResponseEntity<Object> findAPrescriptionByPaciente(UUID pacienteId) {
        if(pacienteRepository.existsById(pacienteId)){
            Optional<PacienteModel> pacienteModelOptional  = pacienteRepository.findById(pacienteId);
            PacienteModel paciente = pacienteModelOptional.get();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(paciente.getPrescricoes());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado");
    }
    @Transactional
    public ResponseEntity<Object> createANewPrescription(PrescricoesModel novaPrescricao) {
        //verificar se existe o médico e o paciente
        if(doctorsRepository.existsById(novaPrescricao.getDoctor().getDoctorsId()) & pacienteRepository.existsById(novaPrescricao.getPaciente().getPacienteId())){
            if(prescricoesRepository.existsByPaciente(novaPrescricao.getPaciente())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Cada paciente só pode receber uma prescricao");
            }
            //cadastrando os novos medicamentos
            for(int i = 0; i < novaPrescricao.getMedicamentos().size(); i++){
                medicamentosRepository.save(novaPrescricao.getMedicamentos().get(i));
            }
            //Salvando no banco
            prescricoesRepository.saveAndFlush(novaPrescricao);
            return ResponseEntity.status(HttpStatus.CREATED).body("Prescricao salva!");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Não foi possível cadatrar a prescricao com os dados informados");
    }

    @Modifying
    public ResponseEntity<Object> deletePrescriptionById(UUID prescriptionId) {
        if(prescricoesRepository.existsById(prescriptionId)) {
            prescricoesRepository.deleteById(prescriptionId);
            return  ResponseEntity.status(HttpStatus.OK).body("Prescricao removida");
        }
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prescricao nao encontrada");
    }

    public ResponseEntity<Object> updatePrescriptionByIds(UUID prescriptionId, PrescricoesModel prescricao) {
        // //verificar se existe o médico e o paciente
        if(doctorsRepository.existsById(prescricao.getDoctor().getDoctorsId()) & pacienteRepository.existsById(prescricao.getPaciente().getPacienteId())){
            //pegando dados do Médico e do Paciente
            Optional<DoctorsModel> doctorsModelOptional = doctorsRepository.findById(prescricao.getDoctor().getDoctorsId());
            Optional<PacienteModel> pacienteModelOptional = pacienteRepository.findById(prescricao.getPaciente().getPacienteId());
            DoctorsModel updatedDoctor = doctorsModelOptional.get();
            PacienteModel updatedPaciente = pacienteModelOptional.get();
            //passando dados
            prescricao.setPrescriptionId(prescriptionId);
            prescricao.setDoctor(updatedDoctor);
            prescricao.setPaciente(updatedPaciente);
            //salvando no banco
            prescricoesRepository.save(prescricao);
            return ResponseEntity.status(HttpStatus.OK).body(prescricao);
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verifique os dados informados");
    }
}
