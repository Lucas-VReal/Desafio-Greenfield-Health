package br.com.GreenfieldHealth.services;

import br.com.GreenfieldHealth.dtos.UsersDto;
import br.com.GreenfieldHealth.models.RoleModel;
import br.com.GreenfieldHealth.models.UsersModel;
import br.com.GreenfieldHealth.repositories.RolesRepository;
import br.com.GreenfieldHealth.repositories.UsersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;

    private final RolesRepository rolesRepository;

    public UsersService(UsersRepository usersRepository, PasswordEncoder encoder, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.encoder = encoder;
        this.rolesRepository = rolesRepository;
    }


    public ResponseEntity<Object> createNewUser(UsersDto usersDto) {
            //Regra de não cadastrar usuários com o mesmo Username
            if(usersRepository.existsByUsername(usersDto.getUsername())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Este Username ja esta sendo utilizado");
             }
            //Verificando condições de Username e Senha
            if(verifyUsernameAndPassword(usersDto) != null){
                return verifyUsernameAndPassword(usersDto);
            }
            //Passando as propriedades validadas para o objeto novoUsuario
            var novoUsuario = new UsersModel();
            BeanUtils.copyProperties(usersDto, novoUsuario);
            //Encriptando a senha
            novoUsuario.setPassword(encoder.encode(novoUsuario.getPassword()));
            //Salvando no banco PostgreSQL (Usuario String, senha encrypted String e id UUID AutoGenerated)
            usersRepository.save(novoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    public ResponseEntity<Object> verifyUsernameAndPassword(UsersDto usersDto){
        //Regra de Username com no mínimo 4 e no máximo 20 caracteres
        if(usersDto.getUsername().length() < 4 || usersDto.getUsername().length() > 20){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("O Username deve ter entre 4 e 20 caracteres");
        }
        //Regra de obrigatoriedade dos caracteres especiais na senha
        if(!usersDto.getPassword().contains("!") && !usersDto.getPassword().contains("@") && !usersDto.getPassword().contains("#") && !usersDto.getPassword().contains("$") && !usersDto.getPassword().contains("%") && !usersDto.getPassword().contains("&") && !usersDto.getPassword().contains("*") && !usersDto.getPassword().contains("(") && !usersDto.getPassword().contains(")") && !usersDto.getPassword().contains("-")){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("A senha deve conter pelo menos um caractere especial !@#$%¨&*()-");
        }
        return null;
    }

    public ResponseEntity<Object> updateUser(UUID id, UsersDto usersDto) {
            //Verificar se usuário existe no banco e se o Username pode ser utilizado
            if(usersRepository.existsById(id)){
                Optional<UsersModel> usersModelOptional = usersRepository.findById(id);
                UsersModel updatedUser = usersModelOptional.get();
                //Verificando condições de Username e Senha
                if(verifyUsernameAndPassword(usersDto) != null){
                    return verifyUsernameAndPassword(usersDto);
                }
                //Atualizando as informações de Username e senha criptografada
                updatedUser.setUsername(usersDto.getUsername());
                updatedUser.setPassword(encoder.encode(usersDto.getPassword()));
                //Excluindo antigas configurações e atribuindo novas
                usersRepository.delete(usersModelOptional.get());
                usersRepository.save(updatedUser);
                return ResponseEntity.status(HttpStatus.OK).body("Usuário atualizado com sucesso!");
            }
            //Caso contrário o sistema apresentará o erro abaixo
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível atualizar o usuário com os dados informados");
    }
    @Transactional
    public ResponseEntity<Object> setRole(String userName, Enum roleEnum, UUID id) {
        //Verificar se existe o Username e Rolename no banco de dados
        if(usersRepository.existsById(id)) {
            //Passando os dados buscados para um novo usuário e role
            Optional<UsersModel> usersModelOptional = usersRepository.findById(id);
            var user = new UsersModel();
            BeanUtils.copyProperties(usersModelOptional.get(), user);
            Optional<RoleModel> roleModelOptional = rolesRepository.findRoleModelByRoleId(rolesRepository.findIdByEnum(roleEnum));
            var role = new RoleModel();
            BeanUtils.copyProperties(roleModelOptional.get(), role);
            //Verificando se os dados do usuário e role não são nulos
            if (user.getUserId() != null && user.getUsername() != null && user.getPassword() != null && role.getRoleId() != null && role.getRoleName() != null){
                //salvando os dados
                List<RoleModel> rolesList = new ArrayList<>();
                rolesList.add(role);
                user.setRoles(null);
                user.setRoles(rolesList);
                usersRepository.save(user);
                return ResponseEntity.accepted().body("Foram concedidas as permissões de " + role.getRoleName() + " para o(a) usuario(a) " + user.getUsername());
            }

        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verifique as informações de usuários/roles e tente novamente");
    }
    @Transactional
    public ResponseEntity<Object> deleteUser(UUID id, UsersDto usersDto) {
        //Verificar se usuário existe no banco e se o Username pode ser utilizado
        if(usersRepository.existsById(id)){
            Optional<UsersModel> usersModelOptional = usersRepository.findById(id);
            UsersModel deletedUser = usersModelOptional.get();
            //Comparando Username e Senha com os dados do Id Informado
            if(usersDto.getUsername().equalsIgnoreCase(deletedUser.getUsername()) && usersDto.getPassword().equalsIgnoreCase(deletedUser.getPassword())){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Os dados de Login e senha não conferem com o ID informado");
            }
            //Deletando no banco PostgreSQL
            usersRepository.delete(deletedUser);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário excluído com sucesso!");
        }
        //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível deletar o usuário com os dados informados");
    }

    public ResponseEntity<Object> deleteUserById(UUID id) {
        //Verificar se usuário existe no banco e se o Username pode ser utilizado
        if(usersRepository.existsById(id)){
            Optional<UsersModel> usersModelOptional = usersRepository.findById(id);
            UsersModel deletedUser = usersModelOptional.get();
            //Deletando no banco PostgreSQL
            usersRepository.delete(deletedUser);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário excluído com sucesso!");
        }
        //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível deletar o usuário com os dados informados");
    }
}