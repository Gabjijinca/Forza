package com.example.Forza.Entity;


import com.example.Forza.Roles.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class OrderClient {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userClent;


    @ManyToOne
    @JoinColumn(name = "service_id")
    private Vehicle servico;


    private String observacao;

    private LocalDate dataPedido;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Vehicle getServico() {
        return servico;
    }

    public void setServico(Vehicle servico) {
        this.servico = servico;
    }

    public User getUserClent() {
        return userClent;
    }

    public void setUserClent(User userClent) {
        this.userClent = userClent;
    }
}
