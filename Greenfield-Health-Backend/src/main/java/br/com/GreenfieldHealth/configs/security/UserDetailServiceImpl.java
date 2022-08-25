package br.com.GreenfieldHealth.configs.security;

import br.com.GreenfieldHealth.models.UsersModel;
import br.com.GreenfieldHealth.repositories.UsersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    public UserDetailServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersModel usuariosModel = usersRepository.findUsuariosModelByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return new User(usuariosModel.getUsername(), usuariosModel.getPassword(), true, true, true,true, usuariosModel.getAuthorities());
    }
}
