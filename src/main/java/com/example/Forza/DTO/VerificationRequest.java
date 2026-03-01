package com.example.Forza.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerificationRequest(@NotBlank @Email String email,
                                  @NotBlank String code) {
}
