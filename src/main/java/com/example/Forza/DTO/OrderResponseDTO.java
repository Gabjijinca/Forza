package com.example.Forza.DTO;

import com.example.Forza.Entity.OrderClient;

import java.util.List;

public record OrderResponseDTO(
        Long id,
        String clienteEmail,
        String servicoTitulo,
        Double preco,
        String observacao,
        String dataPedido,
        String status
) {
    public static OrderResponseDTO fromEntity(OrderClient order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getUserClent().getEmail(),
                order.getServico().getTitulo(),
                order.getServico().getPreco(),
                order.getObservacao(),
                order.getDataPedido().toString(),
                order.getOrderStatus().toString()
        );

    }

}