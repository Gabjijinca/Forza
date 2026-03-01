package com.example.Forza.DTO;

import com.example.Forza.Entity.OrderClient;
import com.example.Forza.Entity.User;
import com.example.Forza.Entity.Vehicle;
import com.example.Forza.Roles.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OrderRequestDTO(
        @NotNull(message = "O serviço não pode ser nulo")
        Integer Idservico,
        String observacao) {



public OrderClient toEntity(Vehicle vehicle,User user){

    OrderClient orderClient = new OrderClient();
    orderClient.setOrderStatus(OrderStatus.EM_ANALISE);
    orderClient.setObservacao(this.observacao);
    orderClient.setDataPedido(LocalDate.now());
    orderClient.setServico(vehicle);
    orderClient.setUserClent(user);
return orderClient;
}





}
