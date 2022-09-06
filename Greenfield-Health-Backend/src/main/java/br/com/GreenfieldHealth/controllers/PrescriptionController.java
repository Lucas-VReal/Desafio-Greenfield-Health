package br.com.GreenfieldHealth.controllers;

import br.com.GreenfieldHealth.domain.dtos.PrescricoesDto;
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

    public PrescriptionController(PrescricoesRepository prescricoesRepository, PrescriptionService prescriptionService) {
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



    @PreAuthorize("hasAnyRole('ROLE_MEDIC', 'ROLE_ADMIN')")
    @PostMapping("/newPrescription/{medicId}")
    public ResponseEntity<Object> createNewPrescription(@PathVariable (name = "medicId") UUID id, @RequestBody @Valid PrescricoesDto prescriptionDto){
        return prescriptionService.createANewPrescription(id, prescriptionDto);
    }





}
