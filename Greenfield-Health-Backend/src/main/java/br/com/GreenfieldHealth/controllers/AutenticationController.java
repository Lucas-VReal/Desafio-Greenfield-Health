package br.com.GreenfieldHealth.controllers;

import br.com.GreenfieldHealth.dtos.UsuariosDto;
import br.com.GreenfieldHealth.models.UsuariosModel;
import br.com.GreenfieldHealth.repositories.UsuariosRepository;
import br.com.GreenfieldHealth.services.UsuariosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/login")
public class AutenticationController {

    private final UsuariosRepository usuariosRepository;
    private final UsuariosService usuariosService;

    public AutenticationController(UsuariosRepository usuariosRepository, UsuariosService usuariosService) {
        this.usuariosRepository = usuariosRepository;
        this.usuariosService = usuariosService;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UsuariosModel>> findAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(usuariosRepository.findAll());
    }
    @PostMapping("/newUser")
    public ResponseEntity<Object> createNewUser(@RequestBody @Valid UsuariosDto usuariosDto){
        return usuariosService.createNewUser(usuariosDto);
    }
    @PutMapping("user/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id, @RequestBody @Valid UsuariosDto usuariosDto){
        //Obs: Se for alterar só o usuário ou só a senha, mesmo assim deve conter os dois no JSON
        return usuariosService.updateUser(id, usuariosDto);
    }
    @DeleteMapping("user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id, @RequestBody @Valid UsuariosDto usuariosDto){
        //Obs: Obrigatório confirmar Login e senha
        return usuariosService.deleteUser(id, usuariosDto);
    }
}
