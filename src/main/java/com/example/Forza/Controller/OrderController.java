package com.example.Forza.Controller;

import com.example.Forza.DTO.OrderRequestDTO;
import com.example.Forza.DTO.OrderResponseDTO;
import com.example.Forza.Entity.OrderClient;
import com.example.Forza.Roles.OrderStatus;
import com.example.Forza.Service.OrderClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderClientService orderService;

    @PostMapping
    public ResponseEntity<?> criarPedido(
            @RequestBody OrderRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        OrderClient orderClient = orderService.realizarPedido(dto, email);


        return ResponseEntity.ok(OrderResponseDTO.fromEntity(orderClient));
    }

    @GetMapping("/my")
    public ResponseEntity<?> verMeusPedidos(@AuthenticationPrincipal UserDetails userDetails) {
        List<OrderClient> orderClients = orderService.listarMeusPedidos(userDetails.getUsername());

        Stream<OrderResponseDTO> orderResponseDTOStream = orderClients.stream().map(OrderResponseDTO::fromEntity);


        return ResponseEntity.ok(orderResponseDTOStream);
    }

    @GetMapping("/all")
    public ResponseEntity<?> verTodosOsPedidos() {

        List<OrderClient> orderClients = orderService.listarTodosOsPedidos();

        Stream<OrderResponseDTO> orderResponseDTOStream = orderClients.stream().map(OrderResponseDTO::fromEntity);


        return ResponseEntity.ok(orderResponseDTOStream);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluirPedido(@PathVariable Long id) {
        orderService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus novoStatus) {

        OrderClient orderClient = orderService.atualizarStatus(id, novoStatus);

        return ResponseEntity.ok(OrderResponseDTO.fromEntity(orderClient));
    }
}
