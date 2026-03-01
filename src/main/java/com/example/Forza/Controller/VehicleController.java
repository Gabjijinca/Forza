package com.example.Forza.Controller;
import com.example.Forza.DTO.ServiceRequestDTO;
import com.example.Forza.Entity.Vehicle;
import com.example.Forza.Service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Vehicle> cadastrarServico(@RequestBody @Valid ServiceRequestDTO dto) {
        return ResponseEntity.ok(vehicleService.AddVehicle(dto));
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> listarTodos() {
        return ResponseEntity.ok(vehicleService.getAll());
    }
}