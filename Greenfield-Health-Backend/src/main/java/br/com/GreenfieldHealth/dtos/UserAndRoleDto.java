package br.com.GreenfieldHealth.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class UserAndRoleDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String roleName;
}
