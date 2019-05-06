package com.grupo1.alojapp.Services;

import com.grupo1.alojapp.Assemblies.AlojamientoAssembly;
import com.grupo1.alojapp.DTOs.AlojamientoDTO;
import com.grupo1.alojapp.Model.Alojamiento;
import com.grupo1.alojapp.Repositories.AlojamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlojamientoService {

    @Autowired
    private AlojamientoRepository alojamientoRepository;

    private AlojamientoAssembly alojamientoAssembly = new AlojamientoAssembly();

    public AlojamientoDTO saveAlojamientoFromDTO(AlojamientoDTO alojamientoDTO){
        Alojamiento alojamiento = alojamientoAssembly.map(alojamientoDTO, Alojamiento.class);
        alojamientoRepository.save(alojamiento);
        alojamientoDTO.setId(alojamiento.getId());
        return alojamientoDTO;
    }

    public void deleteAlojamientoById(Long id){
        Alojamiento alojamiento = alojamientoRepository.getOne(id);
        alojamiento.setEliminado(true);
        alojamientoRepository.save(alojamiento);
    }

    public AlojamientoDTO getById(Long id){
        Alojamiento alojamiento = alojamientoRepository.getOne(id);
        return alojamientoAssembly.map(alojamiento, AlojamientoDTO.class);
    }

    public List<AlojamientoDTO> getAllVigentes(){
        List<Alojamiento> alojamientos = alojamientoRepository.findAll();
        alojamientos = alojamientos.stream().filter(alojamiento -> !alojamiento.isEliminado()).collect(Collectors.toList());
        return convertAlojamientoListToDTO(alojamientos);
    }

    private List<AlojamientoDTO> convertAlojamientoListToDTO(List<Alojamiento> alojamientos){
        List<AlojamientoDTO> alojamientosDTO = new ArrayList<AlojamientoDTO>();
        alojamientos.forEach(alojamiento -> alojamientosDTO.add(alojamientoAssembly.map(alojamiento, AlojamientoDTO.class)));
        return alojamientosDTO;
    }

}
