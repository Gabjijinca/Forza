package com.example.Forza.DTO;

import com.example.Forza.Roles.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
                    @NotBlank(message = "O e-mail é obrigatório")
                    String email,
                      @NotBlank(message = "A senha é obrigatória")
                      String password,
                      @NotNull(message = "A role é obrigatória")
                      UserRole userRole) {
}
