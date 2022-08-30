package br.com.GreenfieldHealth.controllers;

import br.com.GreenfieldHealth.dtos.MedicamentosDto;
import br.com.GreenfieldHealth.repositories.MedicamentosRepository;
import br.com.GreenfieldHealth.services.MedicamentosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/medicaments")
public class MedicamentosController {

    private final MedicamentosRepository medicamentosRepository;
    private final MedicamentosService medicamentosService;

    public MedicamentosController(MedicamentosRepository medicamentosRepository, MedicamentosService medicamentosService) {
        this.medicamentosRepository = medicamentosRepository;
        this.medicamentosService = medicamentosService;
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC')")
    @GetMapping("/getAllMedicaments")
    public ResponseEntity<Object> getAllMedicaments(){
        return ResponseEntity.status(HttpStatus.OK).body(medicamentosRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC')")
    @GetMapping("/getOneMedicament/{id}")
    public ResponseEntity<Object> getOneMedicament(@PathVariable (name = "id")UUID id) {
        return medicamentosService.getOneMedicament(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC')")
    @PostMapping("/newMedicament")
    public ResponseEntity<Object> createNewMedicament(@RequestBody @Valid MedicamentosDto medicamentosDto) {
        return medicamentosService.createNewMedicament(medicamentosDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC')")
    @PutMapping("/updateMedicament/{id}")
    public ResponseEntity<Object> updateMedicamentById(@PathVariable (name = "id") UUID id, @RequestBody @Valid MedicamentosDto medicamentosDto) {
        return medicamentosService.updateMedicamentById(id, medicamentosDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC')")
    @DeleteMapping("/deleteMedicament/{id}")
    public ResponseEntity<Object> deleteMedicamentById(@PathVariable (name = "id") UUID id) {
        return medicamentosService.deleteMedicamentById(id);
    }

}
