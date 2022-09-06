package br.com.GreenfieldHealth.domain.services;

import br.com.GreenfieldHealth.domain.dtos.PrescricoesDto;
import br.com.GreenfieldHealth.domain.models.MedicamentosModel;
import br.com.GreenfieldHealth.domain.models.MedicosModel;
import br.com.GreenfieldHealth.domain.models.PacienteModel;
import br.com.GreenfieldHealth.domain.models.PrescricoesModel;
import br.com.GreenfieldHealth.repositories.MedicosRepository;
import br.com.GreenfieldHealth.repositories.PacienteRepository;
import br.com.GreenfieldHealth.repositories.PrescricoesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PrescriptionService {

    private final PrescricoesRepository prescricoesRepository;
    private final MedicosRepository medicosRepository;
    private final PacienteRepository pacienteRepository;

    public PrescriptionService(PrescricoesRepository prescricoesRepository, MedicosRepository medicosRepository, PacienteRepository pacienteRepository) {
        this.prescricoesRepository = prescricoesRepository;
        this.medicosRepository = medicosRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public ResponseEntity<Object> findAllPrescriptionsByMedicId(UUID medicalId) {
        if(medicosRepository.existsById(medicalId)){
            Optional<MedicosModel> medicosModelOptional  = medicosRepository.findById(medicalId);
            MedicosModel medico = medicosModelOptional.get();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(medico.getPrescricoes());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado");
    }

    @Transactional
    public ResponseEntity<Object> createANewPrescription(UUID id, PrescricoesDto prescricoesDto) {
        //verificar se existe o médico e o paciente
        if(medicosRepository.existsById(id) & pacienteRepository.existsById(prescricoesDto.getPaciente().getPacienteId())){
            //definindo o médico autor da prescricao
            Optional<MedicosModel> optionalMedicosModel = medicosRepository.findById(id);
            MedicosModel medicoAutor = optionalMedicosModel.get();
            //definindo o paciente receptor da prescricao
            Optional<PacienteModel> pacienteModelOptional = pacienteRepository.findById(prescricoesDto.getPaciente().getPacienteId());
            PacienteModel pacienteReceptor = pacienteModelOptional.get();
            if(prescricoesRepository.existsByPaciente(pacienteReceptor)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Cada paciente só pode receber uma prescricao");
            }
            //Atribuindo medicamentos
            List<MedicamentosModel> listaMedicamentos = prescricoesDto.getMedicamentos();
            //criando a prescricao
            var novaPrescricao = new PrescricoesModel();
            novaPrescricao.setDescription(prescricoesDto.getDescription());
            novaPrescricao.setMedico(medicoAutor);novaPrescricao.setPaciente(pacienteReceptor);novaPrescricao.setMedicamentos(listaMedicamentos);
            prescricoesRepository.saveAndFlush(novaPrescricao);
            return ResponseEntity.status(HttpStatus.CREATED).body("Prescricao salva!");
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Não foi possível cadatrar a prescricao com os dados informados");
    }

}
