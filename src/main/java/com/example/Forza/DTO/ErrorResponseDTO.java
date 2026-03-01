package com.example.Forza.DTO;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        String mensagem,
        int status,
        LocalDateTime timestamp
) {}
