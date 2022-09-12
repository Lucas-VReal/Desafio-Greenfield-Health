package br.com.GreenfieldHealth.controllers;

import br.com.GreenfieldHealth.domain.Mappers.PrescricoesMapper;
import br.com.GreenfieldHealth.domain.dtos.PrescricoesDto;
import br.com.GreenfieldHealth.domain.models.PrescricoesModel;
import br.com.GreenfieldHealth.repositories.MedicamentosRepository;
import br.com.GreenfieldHealth.repositories.PrescricoesRepository;
import br.com.GreenfieldHealth.domain.services.PrescriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/medics/prescription")
@CrossOrigin(origins = "*")
public class PrescriptionController {

    private final PrescricoesRepository prescricoesRepository;

    private final PrescriptionService prescriptionService;


    public PrescriptionController(PrescricoesRepository prescricoesRepository, PrescriptionService prescriptionService, MedicamentosRepository medicamentosRepository) {
        this.prescricoesRepository = prescricoesRepository;
        this.prescriptionService = prescriptionService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/allPrescriptions")
    public ResponseEntity<Object> findAllPrescriptions(){
        return ResponseEntity.status(HttpStatus.OK).body(prescricoesRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC')")
    @GetMapping("/allPrescriptionsByMedicalId/{medicalId}")
    public ResponseEntity<Object> findAllPrescriptionsByMedicId(@PathVariable (name = "medicalId") UUID medicalId){
        return prescriptionService.findAllPrescriptionsByMedicId(medicalId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC', 'ROLE_PACIENT')")
    @GetMapping("/prescriptionByPacienteId/{pacienteId}")
    public ResponseEntity<Object> findAPrescriptionByPacienteId(@PathVariable (name = "pacienteId") UUID pacienteId){
        return prescriptionService.findAPrescriptionByPaciente(pacienteId);
    }

    @PreAuthorize("hasAnyRole('ROLE_MEDIC', 'ROLE_ADMIN')")
    @PostMapping("/newPrescription/")
    public ResponseEntity<Object> createNewPrescription(@RequestBody @Valid PrescricoesDto prescriptionDto){
        PrescricoesModel prescription = PrescricoesMapper.INSTANCE.toEntity(prescriptionDto);
        return prescriptionService.createANewPrescription(prescription);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC', 'ROLE_PACIENT')")
    @PutMapping("updatePrescription/{id}")
    public ResponseEntity<Object> updatePrescriptionById (@PathVariable (name = "id") UUID prescriptionId, @RequestBody PrescricoesDto prescricoesDto){
        PrescricoesModel prescricao = PrescricoesMapper.INSTANCE.toEntity(prescricoesDto);
        return prescriptionService.updatePrescriptionByIds(prescriptionId, prescricao);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deletePrescriptionById/{prescriptionId}")
    public ResponseEntity<Object> deletePrescriptionById(@PathVariable (name = "prescriptionId") UUID prescriptionId){
        return prescriptionService.deletePrescriptionById(prescriptionId);
    }


}
