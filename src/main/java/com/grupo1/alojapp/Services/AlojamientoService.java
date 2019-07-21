package com.grupo1.alojapp.Services;

import com.grupo1.alojapp.Assemblies.AlojamientoAssembly;
import com.grupo1.alojapp.Assemblies.CloudFileAssembly;
import com.grupo1.alojapp.DTOs.AlojamientoDTO;
import com.grupo1.alojapp.Exceptions.AlojamientoEliminadoException;
import com.grupo1.alojapp.Model.Alojamiento;
import com.grupo1.alojapp.Model.CloudFile;
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


    private AlojamientoDTO getAlojamientoVigenteById(Long id) throws AlojamientoEliminadoException{
        Alojamiento alojamiento = alojamientoRepository.getOne(id);
        if(alojamiento.isEliminado())
            throw new AlojamientoEliminadoException("El alojamiento de id:"+ id+" se encuentra eliminado");
        return alojamientoAssembly.map(alojamiento, AlojamientoDTO.class);
    }

    public AlojamientoDTO saveAlojamientoFromDTO(AlojamientoDTO alojamientoDTO){
        Alojamiento alojamiento = alojamientoAssembly.map(alojamientoDTO, Alojamiento.class);
        alojamientoRepository.save(alojamiento);
        alojamientoDTO.setId(alojamiento.getId());
        return alojamientoDTO;
    }

    public void deleteAlojamientoById(Long id) throws AlojamientoEliminadoException{
        Alojamiento alojamiento = alojamientoRepository.getOne(id);
        if(alojamiento.isEliminado())
            throw new AlojamientoEliminadoException("Este alojamiento YA se encuentra eliminado");
        alojamiento.setEliminado(true);
        alojamientoRepository.save(alojamiento);
    }

    public AlojamientoDTO getById(Long id) throws AlojamientoEliminadoException{
        return getAlojamientoVigenteById(id);
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

    public AlojamientoDTO addCloudFileToAlojamiento(CloudFile cloudFile, long idAlojamiento){
        Alojamiento alojamiento = alojamientoRepository.getOne(idAlojamiento);
        alojamiento.addReferenceFile(cloudFile);
        alojamientoRepository.save(alojamiento);
        return alojamientoAssembly.map(alojamiento, AlojamientoDTO.class);
    }

    //TODO: Refactor
    private List<Alojamiento> getAlojamientosConFile(long idFile){
        List<Alojamiento> alojamientos = alojamientoRepository.findAll();
        List<Alojamiento> alojamientosConFile = new ArrayList<>();
        alojamientos.forEach(
                alo ->{
                    alo.getReferenceFiles().forEach(
                            rf -> {
                                if(rf.getId() == idFile){
                                    alojamientosConFile.add(alo);
                                }
                            }
                    );
                }
        );
        return alojamientosConFile;
    }

    //TODO: Refactor
    public void deleteFileFromAlojamiento(CloudFile cloudFile){
        List<Alojamiento> alojamientos = getAlojamientosConFile(cloudFile.getId());
        alojamientos.forEach(
                alo ->{
                    alo.getReferenceFiles().remove(cloudFile);
                    alojamientoRepository.save(alo);
                }
        );
    }

    public AlojamientoDTO checkAlojamiento(long idAlojamiento){
        Alojamiento alojamiento = alojamientoRepository.getOne(idAlojamiento);
        alojamiento.setChecked(true);
        return alojamientoAssembly.map(alojamiento,AlojamientoDTO.class);
    }

    public AlojamientoDTO uncheckAlojamiento(long idAlojamiento, String motivoRechazo){
        Alojamiento alojamiento = alojamientoRepository.getOne(idAlojamiento);
        alojamiento.setChecked(false);
        alojamiento.setJustificacionRechazo(motivoRechazo);
        return alojamientoAssembly.map(alojamiento,AlojamientoDTO.class);
    }

}
