package com.example.Forza.Reposit;

import com.example.Forza.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceReposit extends JpaRepository<Vehicle,Long> {

    boolean existsByTituloIgnoreCase(String titulo);
}
