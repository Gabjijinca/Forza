package com.example.Forza.DTO;

import com.example.Forza.Entity.Vehicle;
import com.example.Forza.Roles.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceRequestDTO(
        @NotBlank(message = "O título não pode ser nulo")
        String titulo,
        @NotBlank(message = "A descrição não pode ser nula")

        String descricao,

        @NotNull(message = "O preço não pode ser nulo")
        Double preco,

        @NotBlank(message = "A categoria não pode ser nula")
        String categoria,

        @NotNull(message = "O status não pode ser nulo")
        Status status) {


    public Vehicle toEntity(){
        Vehicle vehicle = new Vehicle();
        vehicle.setCategoria(this.categoria);
        vehicle.setDescricao(this.descricao);
        vehicle.setPreco(this.preco);
        vehicle.setStatus(this.status);
        vehicle.setTitulo(this.titulo);
        return vehicle;

    }



}
