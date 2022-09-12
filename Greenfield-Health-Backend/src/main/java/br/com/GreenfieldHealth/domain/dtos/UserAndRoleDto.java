package br.com.GreenfieldHealth.domain.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserAndRoleDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String roleName;
}
