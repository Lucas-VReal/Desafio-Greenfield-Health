package br.com.GreenfieldHealth.services;

import br.com.GreenfieldHealth.dtos.PacienteDto;
import br.com.GreenfieldHealth.models.PacienteModel;
import br.com.GreenfieldHealth.repositories.PacienteRepository;
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

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
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
    public ResponseEntity<Object> updatePacient(UUID id, PacienteDto pacienteDto) {
        //Verificar se usuário existe no banco e se o CPF pode ser utilizado
        if(pacienteRepository.existsById(id)){
            Optional<PacienteModel> PacienteModelOptional = pacienteRepository.findById(id);
            PacienteModel updatedPacient = PacienteModelOptional.get();
            //Atualizando as informações conforme o JSON
            BeanUtils.copyProperties(pacienteDto, updatedPacient);
            //Excluindo antigos dados e salvando novos no banco
            pacienteRepository.save(updatedPacient);
            pacienteRepository.flush();
            return ResponseEntity.status(HttpStatus.OK).body(updatedPacient);
        }
        //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível atualizar o Médico com os dados informados");
    }

    public ResponseEntity<Object> deletePacientById(UUID id) {
        if(pacienteRepository.existsById(id)){
            Optional<PacienteModel> PacienteModelOptional = pacienteRepository.findById(id);
            PacienteModel deletedPacient = PacienteModelOptional.get();
            //Removendo medico
            pacienteRepository.delete(deletedPacient);
            pacienteRepository.flush();
            return ResponseEntity.status(HttpStatus.OK).body("Paciente deletado com sucesso!");
        }
        //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível remover o Paciente com os dados informados");
    }
}
