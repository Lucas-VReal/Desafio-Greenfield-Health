package br.com.GreenfieldHealth.controllers;

import br.com.GreenfieldHealth.dtos.UserAndRoleDto;
import br.com.GreenfieldHealth.dtos.UsersDto;
import br.com.GreenfieldHealth.enums.RoleName;
import br.com.GreenfieldHealth.models.UsersModel;
import br.com.GreenfieldHealth.repositories.UsersRepository;
import br.com.GreenfieldHealth.services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersRepository usersRepository;
    private final UsersService userService;

    public UsersController(UsersRepository usersRepository, UsersService userService) {
        this.usersRepository = usersRepository;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/allUsers")
    public ResponseEntity<List<UsersModel>> findAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(usersRepository.findAll());
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC', 'ROLE_PACIENT')")
    @PostMapping("/newUser")
    public ResponseEntity<Object> createNewUser(@RequestBody @Valid UsersDto usersDto){
        return userService.createNewUser(usersDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/setRole")
    public ResponseEntity<Object> setRole(@RequestBody UserAndRoleDto userAndRoleDto){
        Enum roleName = RoleName.valueOf(userAndRoleDto.getRoleName());
        return userService.setRole(userAndRoleDto.getUserName(), roleName);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEDIC', 'ROLE_PACIENT')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id, @RequestBody @Valid UsersDto usersDto){
        //Obs: Se for alterar só o usuário ou só a senha, mesmo assim deve conter os dois no JSON
        return userService.updateUser(id, usersDto);
    }




}
