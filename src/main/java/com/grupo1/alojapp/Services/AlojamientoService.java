package com.grupo1.alojapp.Services;

import com.grupo1.alojapp.Controllers.AlojamientoController;
import com.grupo1.alojapp.Model.Pension;
import com.grupo1.alojapp.DTOs.PensionDTO;
import com.grupo1.alojapp.Model.CloudFile;
import com.grupo1.alojapp.Model.Alojamiento;
import com.grupo1.alojapp.DTOs.AlojamientoDTO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.grupo1.alojapp.DTOs.DeletePensionDTO;
import com.grupo1.alojapp.Assemblies.AlojamientoAssembly;
import com.grupo1.alojapp.Repositories.AlojamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.grupo1.alojapp.Exceptions.AlojamientoEliminadoException;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class AlojamientoService {

    static Logger log = Logger.getLogger(AlojamientoController.class.getName());

    @Autowired
    private AlojamientoRepository alojamientoRepository;
    @Autowired
    private PensionService pensionService;

    private AlojamientoAssembly alojamientoAssembly = new AlojamientoAssembly();


    private AlojamientoDTO getAlojamientoVigenteById(Long id) throws AlojamientoEliminadoException{
        Alojamiento alojamiento = alojamientoRepository.getOne(id);
        if(alojamiento.isEliminado()){
            String msj_error = "El alojamiento de id:"+ id+" se encuentra eliminado";
            log.error(msj_error);
            throw new AlojamientoEliminadoException(msj_error);
        }
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
        if(alojamiento.isEliminado()){
            String msj_error = "El alojamiento de id:"+ id+" se encuentra eliminado";
            log.error(msj_error);
            throw new AlojamientoEliminadoException(msj_error);
        }
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

    public List<AlojamientoDTO> getAllPorValidar(){
        List<AlojamientoDTO> alojamientosDTO =
         getAllVigentes().stream().filter(alojamiento -> alojamiento.getChecked() == null).collect(Collectors.toList());
        return alojamientosDTO;
    }

    public List<AlojamientoDTO> getAllValidados(){
        List<AlojamientoDTO> alojamientosDTO =
                getAllVigentes().stream().filter(alojamiento ->
                    alojamiento.getChecked() != null
                    && alojamiento.getChecked()
                ).collect(Collectors.toList());
        return alojamientosDTO;
    }

    public List<AlojamientoDTO> getAllRechazados(){
        List<AlojamientoDTO> alojamientosDTO =
                getAllVigentes().stream().filter(alojamiento ->
                        alojamiento.getChecked() != null
                                && !alojamiento.getChecked()
                ).collect(Collectors.toList());
        return alojamientosDTO;
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
        Boolean checked = new Boolean(true);
        alojamiento.setChecked(checked);
        alojamientoRepository.save(alojamiento);
        return alojamientoAssembly.map(alojamiento,AlojamientoDTO.class);
    }

    public AlojamientoDTO uncheckAlojamiento(long idAlojamiento, String motivoRechazo){
        Alojamiento alojamiento = alojamientoRepository.getOne(idAlojamiento);
        Boolean checked = new Boolean(false);
        alojamiento.setChecked(checked);
        alojamiento.setJustificacionRechazo(motivoRechazo);
        alojamientoRepository.save(alojamiento);
        return alojamientoAssembly.map(alojamiento,AlojamientoDTO.class);
    }

    public AlojamientoDTO agregarPension(long idAlojamiento, Pension pension){
        Alojamiento alojamiento = alojamientoRepository.getOne(idAlojamiento);
        alojamiento.addPension(pension);
        alojamientoRepository.save(alojamiento);
        return alojamientoAssembly.map(alojamiento, AlojamientoDTO.class);
    }

    public AlojamientoDTO eliminarPension(long idPension){
    	Pension pension = pensionService.getPension(idPension);
    	Optional<Alojamiento> alojamiento = alojamientoRepository.findAll().stream().filter(alojam -> alojam.getPensiones().contains(pension)).findFirst();
    	if(alojamiento.isPresent()) {
            alojamiento.get().getPensiones().remove(pension);
            alojamientoRepository.save(alojamiento.get());
            return alojamientoAssembly.map(alojamiento, AlojamientoDTO.class);
    	}
    	return new AlojamientoDTO();
    }

    public boolean modificarPensionSiExiste(AlojamientoDTO alojamientoDTO, PensionDTO pensionDTO){
        Optional<PensionDTO> posiblePensionDTO = alojamientoDTO.getPensiones().stream().filter(pen -> pen.getTipopension() == pensionDTO.getTipopension()).findFirst();
        if(!posiblePensionDTO.isPresent()) return false;
        pensionService.modificarPensionExistente(posiblePensionDTO.get(), pensionDTO);
        return true;
    }

}
