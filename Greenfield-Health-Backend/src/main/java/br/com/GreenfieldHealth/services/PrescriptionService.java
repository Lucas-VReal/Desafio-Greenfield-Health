package br.com.GreenfieldHealth.services;

import br.com.GreenfieldHealth.dtos.PrescricoesDto;
import br.com.GreenfieldHealth.models.MedicosModel;
import br.com.GreenfieldHealth.models.PrescricoesModel;
import br.com.GreenfieldHealth.repositories.MedicosRepository;
import br.com.GreenfieldHealth.repositories.PacienteRepository;
import br.com.GreenfieldHealth.repositories.PrescricoesRepository;
import org.springframework.beans.BeanUtils;
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

    public PrescriptionService(PrescricoesRepository prescricoesRepository, MedicosRepository medicosRepository, PacienteRepository pacienteRepository) {
        this.prescricoesRepository = prescricoesRepository;
        this.medicosRepository = medicosRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public ResponseEntity<Object> createANewPrescription(UUID id, PrescricoesDto prescricoesDto) {

        if (medicosRepository.existsById(id) & pacienteRepository.existsById(prescricoesDto.getPaciente().getPacienteId())) {
            //Copiando dados de uma prescricao para outra
            PrescricoesModel novaPrescricao = new PrescricoesModel();
            BeanUtils.copyProperties(prescricoesDto, novaPrescricao);
            //Atribuindo médico
            Optional<MedicosModel> medicosModelOptional = medicosRepository.findById(id);
            MedicosModel medico = medicosModelOptional.get();
            novaPrescricao.setMedico(medico);
            //Salvando nova prescricao
            prescricoesRepository.save(novaPrescricao);
            prescricoesRepository.flush();
            return ResponseEntity.status(HttpStatus.CREATED).body(novaPrescricao);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID ou dados não encontrados");
    }


}
