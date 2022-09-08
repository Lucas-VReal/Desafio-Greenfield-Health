package br.com.GreenfieldHealth.controllers;

import br.com.GreenfieldHealth.domain.Mappers.PacienteMapper;
import br.com.GreenfieldHealth.domain.dtos.PacienteDto;
import br.com.GreenfieldHealth.domain.models.PacienteModel;
import br.com.GreenfieldHealth.repositories.PacienteRepository;
import br.com.GreenfieldHealth.domain.services.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/pacients")
@CrossOrigin(origins = "*")
public class PacienteController {

    private final PacienteRepository pacienteRepository;
    private final PacienteService pacienteService;

    public PacienteController(PacienteRepository pacienteRepository, PacienteService pacienteService) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteService = pacienteService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC', 'ROLE_PACIENT')")
    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllPacients(){
        return ResponseEntity.status(HttpStatus.OK).body(pacienteRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC', 'ROLE_PACIENT')")
    @GetMapping("/getOnePacient/{id}")
    public ResponseEntity<Object> getOnePacient(@PathVariable (name = "id") UUID id){
        return pacienteService.getOnePacient(id);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC', 'ROLE_PACIENT')")
    @PostMapping("/newPacient")
    public ResponseEntity<Object> newPacient(@RequestBody @Valid PacienteDto pacienteDto){
        return pacienteService.createNewPacient(pacienteDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC', 'ROLE_PACIENT')")
    @PutMapping("updatePacient/{id}")
    public ResponseEntity<Object> updatePacient(@PathVariable (name = "id") UUID id, @RequestBody @Valid PacienteDto pacienteDto){
        PacienteModel paciente = PacienteMapper.INSTANCE.toEntity(pacienteDto);
        return pacienteService.updatePacient(id, paciente);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("deletePacientById/{id}")
    public ResponseEntity<Object> updatePacient(@PathVariable (name = "id") UUID id){
        return pacienteService.deletePacientById(id);
    }

}
