package br.com.GreenfieldHealth.domain.services;

import br.com.GreenfieldHealth.domain.dtos.PacienteDto;
import br.com.GreenfieldHealth.domain.models.PacienteModel;
import br.com.GreenfieldHealth.domain.models.PrescricoesModel;
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
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    private final PrescricoesRepository prescricoesRepository;

    public PacienteService(PacienteRepository pacienteRepository, PrescricoesRepository prescricoesRepository) {
        this.pacienteRepository = pacienteRepository;
        this.prescricoesRepository = prescricoesRepository;
    }

    public ResponseEntity<Object> createNewPacient(PacienteDto pacienteDto) {
        //Regra de não cadastrar pacientes que com o mesmo CPF
        if(pacienteRepository.existsByCpf(pacienteDto.getCpf())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este CPF ja esta sendo utilizado");
        }
        //Passando as propriedades validadas para o objeto novoPaciente
        var novoPaciente = new PacienteModel();
        BeanUtils.copyProperties(pacienteDto, novoPaciente);
        //Salvando no banco PostgreSQL
        pacienteRepository.save(novoPaciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPaciente);
    }

    public ResponseEntity<Object> getOnePacient(UUID id) {
        if(pacienteRepository.existsById(id)){
            Optional<PacienteModel> optionalPacienteModel = pacienteRepository.findById(id);
            PacienteModel pacient = optionalPacienteModel.get();
            return ResponseEntity.status(HttpStatus.OK).body(pacient);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum paciente encontrado com esse Id");
    }

    @Transactional
    public ResponseEntity<Object> updatePacient(UUID id, PacienteModel paciente) {
        //Verificar se usuário existe no banco e se o CPF pode ser utilizado
        if(pacienteRepository.existsById(id)){
            Optional<PacienteModel> PacienteModelOptional = pacienteRepository.findById(id);
            PacienteModel checkPacient = PacienteModelOptional.get();
            if(!checkPacient.getCpf().equalsIgnoreCase(paciente.getCpf()) && pacienteRepository.existsByCpf(paciente.getCpf())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Este CPF já está associado a outro usuário");
            }
            //Salvando novos no banco
            paciente.setPacienteId(checkPacient.getPacienteId());
            pacienteRepository.save(paciente);
            pacienteRepository.flush();
            return ResponseEntity.status(HttpStatus.OK).body(checkPacient);
        }
        //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível atualizar o Médico com os dados informados");
    }

    public ResponseEntity<Object> deletePacientById(UUID id) {
        if(pacienteRepository.existsById(id)){
            Optional<PacienteModel> PacienteModelOptional = pacienteRepository.findById(id);
            PacienteModel deletedPacient = PacienteModelOptional.get();
            //Gambiarra
            for(int i = 0; i < deletedPacient.getPrescricoes().size(); i++){
                PrescricoesModel prescription = deletedPacient.getPrescricoes().get(i);
                prescription.setPaciente(null);
                prescricoesRepository.save(prescription);
            }
            //Removendo paciente
            pacienteRepository.deleteById(id);
            pacienteRepository.flush();
            return ResponseEntity.status(HttpStatus.OK).body("Paciente deletado com sucesso!");
        }
        //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível remover o Paciente com os dados informados");
    }
}
