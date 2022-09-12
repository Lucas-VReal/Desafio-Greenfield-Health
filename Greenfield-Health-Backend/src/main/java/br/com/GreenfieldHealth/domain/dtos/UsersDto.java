package br.com.GreenfieldHealth.domain.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UsersDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
