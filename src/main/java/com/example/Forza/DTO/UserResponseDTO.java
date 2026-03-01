package com.example.Forza.DTO;

import com.example.Forza.Entity.User;
import com.example.Forza.Roles.UserRole;

public record UserResponseDTO(Long id, String email, UserRole userRole, boolean enabled) {



public static UserResponseDTO toResponse(User user){
    return new UserResponseDTO(user.getId(), user.getEmail(), user.getUserRole(), user.isEnabled());
}




}
