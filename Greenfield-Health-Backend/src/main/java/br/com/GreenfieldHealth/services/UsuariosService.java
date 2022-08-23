package br.com.GreenfieldHealth.services;

import br.com.GreenfieldHealth.dtos.UsuariosDto;
import br.com.GreenfieldHealth.models.UsuariosModel;
import br.com.GreenfieldHealth.repositories.UsuariosRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;

    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public ResponseEntity<Object> createNewUser(UsuariosDto usuariosDto) {
        //Verificando condições de Login e Senha
        if(verifyLoginAndPassword(usuariosDto) != null){
            return verifyLoginAndPassword(usuariosDto);
        }
        //Passando as propriedades validadas para o objeto novoUsuario
        var novoUsuario = new UsuariosModel();
        BeanUtils.copyProperties(usuariosDto, novoUsuario);
        novoUsuario.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC-3")));
        //Salvando no banco PostgreSQL
        usuariosRepository.save(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    public ResponseEntity<Object> updateUser(Long id, UsuariosDto usuariosDto) {
        //Verificar se usuário existe no banco e se o Login pode ser utilizado
        if(usuariosRepository.existsById(id)){
            Optional<UsuariosModel> usuariosModelOptional = usuariosRepository.findById(id);
            UsuariosModel updatedUser = usuariosModelOptional.get();
            //Verificando condições de Login e Senha
            if(verifyLoginAndPassword(usuariosDto) != null){
                return verifyLoginAndPassword(usuariosDto);
            }
            //Atualizando as informações de Login e senha
            updatedUser.setLogin(usuariosDto.getLogin());
            updatedUser.setPassword(usuariosDto.getPassword());
            updatedUser.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC-3")));
            //Salvando no banco PostgreSQL
            usuariosRepository.save(updatedUser);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário atualizado com sucesso!");
        }
        //Caso contrário o sistema apresentará o erro abaixo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível atualizar o usuário com os dados informados");
    }

    @Transactional
    public ResponseEntity<Object> deleteUser(Long id, UsuariosDto usuariosDto) {
        Optional <UsuariosModel> usuariosModelOptional = usuariosRepository.findById(id);
        //Verificar se contêm dados desse usuário no banco
        if(!usuariosModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum usuário encontrado para o Id informado");
        }
        UsuariosModel deletedUser = usuariosModelOptional.get();
        //Se os dados informados na confirmação forem divergentes do Id
        if (!usuariosDto.getLogin().equalsIgnoreCase(deletedUser.getLogin()) || !usuariosDto.getPassword().equalsIgnoreCase(deletedUser.getPassword())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Os dados informados na confirmação estão divergentes");
        }
        //Usuário deletado do banco
        usuariosRepository.delete(deletedUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuário excluído com sucesso");
    }

    public ResponseEntity<Object> verifyLoginAndPassword(UsuariosDto usuariosDto){
        //Regra de não cadastrar usuários que com o mesmo Login
        if(usuariosRepository.existsByLogin(usuariosDto.getLogin())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Login Indisponível");
        }
        //Regra de Login com no mínimo 4 e no máximo 20 caracteres
        if(usuariosDto.getLogin().length() < 4 || usuariosDto.getLogin().length() > 20){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("O Login deve ter entre 4 e 20 caracteres");
        }
        //Regra de obrigatoriedade dos caracteres especiais na senha
        if(!usuariosDto.getPassword().contains("!") && !usuariosDto.getPassword().contains("@") && !usuariosDto.getPassword().contains("#") && !usuariosDto.getPassword().contains("$") && !usuariosDto.getPassword().contains("%") && !usuariosDto.getPassword().contains("&") && !usuariosDto.getPassword().contains("*") && !usuariosDto.getPassword().contains("(") && !usuariosDto.getPassword().contains(")") && !usuariosDto.getPassword().contains("-")){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("A senha deve conter pelo menos um caractere especial !@#$%¨&*()-");
        }
        return null;
    }
}
