package br.com.GreenfieldHealth.services;

import br.com.GreenfieldHealth.dtos.MedicamentosDto;
import br.com.GreenfieldHealth.models.MedicamentosModel;
import br.com.GreenfieldHealth.repositories.MedicamentosRepository;
import br.com.GreenfieldHealth.repositories.PrescricoesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MedicamentosService {
    private final MedicamentosRepository medicamentosRepository;
    private final PrescricoesRepository prescricoesRepository;

    public MedicamentosService(MedicamentosRepository medicamentosRepository, PrescricoesRepository prescricoesRepository) {
        this.medicamentosRepository = medicamentosRepository;
        this.prescricoesRepository = prescricoesRepository;
    }

    public ResponseEntity<Object> getOneMedicament(UUID id) {
        if(medicamentosRepository.existsById(id)){
            Optional<MedicamentosModel> optionalMedicamentosModel = medicamentosRepository.findById(id);
            MedicamentosModel medicament = optionalMedicamentosModel.get();
            return ResponseEntity.status(HttpStatus.OK).body(medicament);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum medicamento encontrado com esse Id");
    }

    public ResponseEntity<Object> createNewMedicament(MedicamentosDto medicamentosDto) {
        var novoMedicamento = new MedicamentosModel();
        BeanUtils.copyProperties(medicamentosDto, novoMedicamento);
        //Salvando no banco PostgreSQL
        medicamentosRepository.save(novoMedicamento);
        medicamentosRepository.flush();
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMedicamento);
    }

    public ResponseEntity<Object> updateMedicamentById(UUID id, MedicamentosDto medicamentosDto) {
        //Verificar se medicamento existe no banco
        if(medicamentosRepository.existsById(id)){
            Optional<MedicamentosModel> MedicamentosModelOptional = medicamentosRepository.findById(id);
            MedicamentosModel updatedMedicament = MedicamentosModelOptional.get();
            //Atualizando as informações conforme o JSON
            BeanUtils.copyProperties(medicamentosDto, updatedMedicament);
            //Excluindo antigos dados e salvando novos no banco
            medicamentosRepository.save(updatedMedicament);
            medicamentosRepository.flush();
            return ResponseEntity.status(HttpStatus.OK).body(updatedMedicament);
        }
        //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível atualizar o medicamento com os dados informados");
    }

    public ResponseEntity<Object> deleteMedicamentById(UUID id) {
        //Verificar se medicamento existe no banco
        if(medicamentosRepository.existsById(id)){
        Optional<MedicamentosModel> MedicamentosModelOptional = medicamentosRepository.findById(id);
        MedicamentosModel deletedMedicament = MedicamentosModelOptional.get();
        //Excluindo Medicamento
        medicamentosRepository.delete(deletedMedicament);
        medicamentosRepository.flush();
        return ResponseEntity.status(HttpStatus.OK).body("Medicamento deletado com sucesso");
    }
    //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível atualizar o medicamento com os dados informados");
    }
}
