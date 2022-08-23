package br.com.GreenfieldHealth.dtos;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class UsuariosDto {
    @NotNull
    private String login;
    @NotNull
    private String password;
}
