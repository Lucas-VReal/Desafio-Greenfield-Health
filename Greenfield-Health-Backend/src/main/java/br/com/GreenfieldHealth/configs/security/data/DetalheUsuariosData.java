package br.com.GreenfieldHealth.configs.security.data;

import br.com.GreenfieldHealth.domain.models.UsersModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DetalheUsuariosData implements UserDetails {

    private final Optional<UsersModel> usuariosModelOptional;

    public DetalheUsuariosData(Optional<UsersModel> usuariosModelOptional) {
        this.usuariosModelOptional = usuariosModelOptional;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return usuariosModelOptional.orElse(new UsersModel()).getPassword();
    }

    @Override
    public String getUsername() {
        return usuariosModelOptional.orElse(new UsersModel()).getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
