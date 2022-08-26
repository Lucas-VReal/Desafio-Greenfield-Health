package br.com.GreenfieldHealth.controllers;

import br.com.GreenfieldHealth.dtos.MedicosDto;
import br.com.GreenfieldHealth.dtos.UsersDto;
import br.com.GreenfieldHealth.models.MedicosModel;
import br.com.GreenfieldHealth.repositories.MedicosRepository;
import br.com.GreenfieldHealth.services.MedicosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medics")
public class MedicalController {

    private final MedicosRepository medicosRepository;

    private final MedicosService medicosService;

    public MedicalController(MedicosRepository medicosRepository, MedicosService medicosService) {
        this.medicosRepository = medicosRepository;
        this.medicosService = medicosService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/allMedics")
    public ResponseEntity<List<MedicosModel>> findAllMedics(){
        return ResponseEntity.status(HttpStatus.OK).body(medicosRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC')")
    @GetMapping("/oneMedic/{id}")
    public ResponseEntity<Object> findOneMedic(@PathVariable(name = "id") UUID medicalId){
        return medicosService.findOneMedicById(medicalId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC')")
    @PostMapping("/newMedic")
    public ResponseEntity<Object> createNewMedic(@RequestBody @Valid MedicosDto medicosDto){
        return medicosService.createANewMedic(medicosDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC')")
    @PutMapping("/updateMedic/{id}")
    public ResponseEntity<Object> updateMedic(@PathVariable(value = "id") UUID id, @RequestBody @Valid MedicosDto medicosDto){
        //Obs: Para a atualização são necessários o ID e todos os dados obrigatórios no JSON
        return medicosService.updateMedic(id, medicosDto);
    }
    /*
    Falta a prescricao:

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/deleteMedic/{id}")
    public ResponseEntity<Object> delteMedicById(@PathVariable(value = "id") UUID id){
        return medicosService.deleteMerdicById(id);
    }*/



}
