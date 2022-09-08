package br.com.GreenfieldHealth.controllers;

import br.com.GreenfieldHealth.domain.Mappers.MedicosMapper;
import br.com.GreenfieldHealth.domain.dtos.DoctorDto;
import br.com.GreenfieldHealth.domain.models.DoctorsModel;
import br.com.GreenfieldHealth.domain.services.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/Doctors")
@CrossOrigin(origins = "*")
public class DoctorsController {

    private final br.com.GreenfieldHealth.repositories.DoctorsRepository DoctorsRepository;

    private final DoctorService doctorService;

    public DoctorsController(br.com.GreenfieldHealth.repositories.DoctorsRepository doctorsRepository, DoctorService doctorService) {
        DoctorsRepository = doctorsRepository;
        this.doctorService = doctorService;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/allDoctors")
    public ResponseEntity<List<DoctorsModel>> findAllDoctors(){
        return ResponseEntity.status(HttpStatus.OK).body(DoctorsRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_Doctor')")
    @GetMapping("/oneDoctor/{id}")
    public ResponseEntity<Object> findOneDoctor(@PathVariable(name = "id") UUID DoctoralId){
        return doctorService.findOneDoctorById(DoctoralId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_Doctor')")
    @PostMapping("/newDoctor")
    public ResponseEntity<Object> createNewDoctor(@RequestBody @Valid DoctorDto DoctorsDto){
        DoctorsModel newDoctor = MedicosMapper.INSTANCE.toEntity(DoctorsDto);
        return doctorService.createANewDoctor(newDoctor);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_Doctor')")
    @PutMapping("/updateDoctor/{id}")
    public ResponseEntity<Object> updateDoctor(@PathVariable(value = "id") UUID id, @RequestBody @Valid DoctorDto DoctorsDto){
        //Obs: Para a atualização são necessários o ID e todos os dados obrigatórios no JSON
        DoctorsModel updatedDoctor = MedicosMapper.INSTANCE.toEntity(DoctorsDto);
        return doctorService.updateDoctor(id, updatedDoctor);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteDoctor/{id}")
    public ResponseEntity<Object> deleteDoctorById(@PathVariable(value = "id") UUID id){
        return doctorService.deleteDoctorById(id);
    }


}
