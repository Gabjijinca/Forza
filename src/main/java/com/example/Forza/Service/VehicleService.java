package com.example.Forza.Service;


import com.example.Forza.DTO.ServiceRequestDTO;
import com.example.Forza.Entity.Vehicle;
import com.example.Forza.Reposit.ServiceReposit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final ServiceReposit serviceReposit;


    public VehicleService(ServiceReposit serviceReposit) {
        this.serviceReposit = serviceReposit;
    }





    public List<Vehicle> getAll(){
        return serviceReposit.findAll();
    }


    public Vehicle AddVehicle(ServiceRequestDTO serviceRequestDTO){

        if(serviceReposit.existsByTituloIgnoreCase(serviceRequestDTO.titulo())){
            throw new IllegalArgumentException("Já existe um serviço cadastrado com este título.");
            }

        Vehicle entity = serviceRequestDTO.toEntity();

        return serviceReposit.save(entity);
    }



    public Vehicle buscarPorId(Long id){

        return serviceReposit.findById(id).orElseThrow(()-> new IllegalArgumentException("Serviço não encontrado"));
    }





}
