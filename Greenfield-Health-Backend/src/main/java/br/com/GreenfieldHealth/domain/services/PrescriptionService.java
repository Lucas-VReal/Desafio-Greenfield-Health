package br.com.GreenfieldHealth.domain.services;


import br.com.GreenfieldHealth.domain.Mappers.PrescricoesMapper;
import br.com.GreenfieldHealth.domain.dtos.PrescricoesDto;
import br.com.GreenfieldHealth.domain.models.MedicosModel;
import br.com.GreenfieldHealth.domain.models.PacienteModel;
import br.com.GreenfieldHealth.domain.models.PrescricoesModel;
import br.com.GreenfieldHealth.repositories.MedicamentosRepository;
import br.com.GreenfieldHealth.repositories.MedicosRepository;
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
    private final MedicosRepository medicosRepository;
    private final PacienteRepository pacienteRepository;

    private final MedicamentosRepository medicamentosRepository;

    public PrescriptionService(PrescricoesRepository prescricoesRepository, MedicosRepository medicosRepository, PacienteRepository pacienteRepository, MedicamentosRepository medicamentosRepository) {
        this.prescricoesRepository = prescricoesRepository;
        this.medicosRepository = medicosRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicamentosRepository = medicamentosRepository;
    }

    public ResponseEntity<Object> findAllPrescriptionsByMedicId(UUID medicalId) {
        if(medicosRepository.existsById(medicalId)){
            Optional<MedicosModel> medicosModelOptional  = medicosRepository.findById(medicalId);
            MedicosModel medico = medicosModelOptional.get();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(medico.getPrescricoes());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado");
    }
    public ResponseEntity<Object> findAPrescriptionByPaciente(UUID pacienteId) {
        if(pacienteRepository.existsById(pacienteId)){
            Optional<PacienteModel> pacienteModelOptional  = pacienteRepository.findById(pacienteId);
            PacienteModel paciente = pacienteModelOptional.get();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(paciente.getPrescricao());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado");
    }
    @Transactional
    public ResponseEntity<Object> createANewPrescription(PrescricoesModel novaPrescricao) {
        //verificar se existe o médico e o paciente
        if(medicosRepository.existsById(novaPrescricao.getMedico().getMedicalId()) & pacienteRepository.existsById(novaPrescricao.getPaciente().getPacienteId())){
            if(prescricoesRepository.existsByPaciente(novaPrescricao.getPaciente())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Cada paciente só pode receber uma prescricao");
            }
            //cadastrando os novos medicamentos
            prescricoesRepository.saveAndFlush(novaPrescricao);
            return ResponseEntity.status(HttpStatus.CREATED).body("Prescricao salva!");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Não foi possível cadatrar a prescricao com os dados informados");
    }

    public ResponseEntity<Object> updatePrescriptionById(UUID prescriptionId, PrescricoesDto prescriptionDto) {
        if(prescricoesRepository.existsById(prescriptionId)){
            PrescricoesModel prescription = PrescricoesMapper.INSTANCE.toEntity(prescriptionDto);
            prescription.setPrescriptionId(prescriptionId);
            //cadastrando os novos medicamentos
            medicamentosRepository.saveAll(prescription.getMedicamentos());
            prescricoesRepository.save(prescription);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(prescription);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verifique o id e os dados informados");
    }

    @Modifying
    public ResponseEntity<Object> deletePrescriptionById(UUID prescriptionId) {
        if(prescricoesRepository.existsById(prescriptionId)) {
            try{
                prescricoesRepository.reallyDeleteById(prescriptionId);
                prescricoesRepository.flush();
            }finally {
                return ResponseEntity.status(HttpStatus.OK).body("Prescricao Removida!");
            }
        }
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prescricao nao encontrada");
    }
}
