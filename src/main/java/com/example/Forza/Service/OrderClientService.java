package com.example.Forza.Service;

import com.example.Forza.DTO.OrderRequestDTO;
import com.example.Forza.Entity.OrderClient;
import com.example.Forza.Entity.User;
import com.example.Forza.Entity.Vehicle;
import com.example.Forza.Reposit.OrderReposit;
import com.example.Forza.Reposit.UserRepository;

import com.example.Forza.Roles.OrderStatus;
import com.example.Forza.Roles.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderClientService {

    @Autowired
    private OrderReposit orderRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserRepository userRepository;



    @Transactional
    public OrderClient realizarPedido(OrderRequestDTO dto, String emailUsuarioLogado) {
        User user = userRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Vehicle vehicle = vehicleService.buscarPorId(Long.valueOf(dto.Idservico()));

        if(vehicle.getStatus().equals(Status.INDISPONIVEL)){
            throw new IllegalArgumentException("Serviço indisponível");
        }

        boolean pedidoDuplicado = orderRepository.existsByUserClentAndServicoAndOrderStatus(
                user, vehicle, OrderStatus.EM_ANALISE);

        if (pedidoDuplicado) {
            throw new IllegalArgumentException("Você já possui uma solicitação em análise para este serviço.");
        }

        OrderClient order = dto.toEntity(vehicle, user);
        return orderRepository.save(order);
    }

    public OrderClient atualizarStatus(Long orderId, OrderStatus novoStatus) {
        OrderClient order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        order.setOrderStatus(novoStatus);
        return orderRepository.save(order);
    }

    public List<OrderClient> listarMeusPedidos(String email) {
        return orderRepository.findByUserClentEmail(email);
    }


    public void deletarPedido(Long id) {
        OrderClient order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        orderRepository.delete(order);
    }

    public List<OrderClient> listarTodosOsPedidos() {
        return orderRepository.findAll();
    }
}