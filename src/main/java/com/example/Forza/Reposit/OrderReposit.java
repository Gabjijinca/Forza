package com.example.Forza.Reposit;

import com.example.Forza.Entity.OrderClient;
import com.example.Forza.Entity.User;
import com.example.Forza.Entity.Vehicle;
import com.example.Forza.Roles.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderReposit extends JpaRepository<OrderClient, Long> {

    boolean existsByUserClentAndServicoAndOrderStatus(User userClent, Vehicle servico, OrderStatus orderStatus);
    List<OrderClient> findByUserClentEmail(String email);
}
