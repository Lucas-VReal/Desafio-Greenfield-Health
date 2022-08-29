package br.com.GreenfieldHealth.controllers;

import br.com.GreenfieldHealth.dtos.MedicosDto;
import br.com.GreenfieldHealth.dtos.PrescricoesDto;
import br.com.GreenfieldHealth.repositories.PrescricoesRepository;
import br.com.GreenfieldHealth.services.PrescriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/medics/prescription")
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

    @PreAuthorize("hasRole('ROLE_MEDIC')")
    @PostMapping("/newPrecription/{id}")
    public ResponseEntity<Object> createNewPrescription(@PathVariable (name = "id") UUID id, @RequestBody @Valid PrescricoesDto prescriptionDto){
        return prescriptionService.createANewPrescription(id, prescriptionDto);
    }



}
