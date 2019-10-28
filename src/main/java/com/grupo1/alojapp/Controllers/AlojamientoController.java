package com.grupo1.alojapp.Controllers;

import com.grupo1.alojapp.DTOs.*;
import com.grupo1.alojapp.Exceptions.AlojamientoEliminadoException;
import com.grupo1.alojapp.Model.Alojamiento;
import com.grupo1.alojapp.Model.CloudFile;
import com.grupo1.alojapp.Model.Pension;
import com.grupo1.alojapp.Model.TIPOPENSION;
import com.grupo1.alojapp.Services.AlojamientoService;
import com.grupo1.alojapp.Services.PensionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE })
public class AlojamientoController {

    static Logger log = Logger.getLogger(AlojamientoController.class.getName());

    @Autowired
    private AlojamientoService alojamientoService;
    @Autowired
    private PensionService pensionService;
    @Autowired
    private CloudFileController cloudFileController;

    @GetMapping("alojamiento")
    @ResponseBody
    public ResponseEntity<List<AlojamientoDTO>> getAlojamientosVigentes(){
        log.info("Se piden todos los alojamientos");
        List<AlojamientoDTO> alojamientosDTO = alojamientoService.getAllVigentes();
        return ResponseEntity.ok(alojamientosDTO);
    }

    @GetMapping("alojamiento/{id}")
    @ResponseBody
    public ResponseEntity<ResponseHttp> getAlojamiento(@PathVariable Long id) throws AlojamientoEliminadoException {
        log.info("Se pide alojamiento de id: "+id);
        try{
            AlojamientoDTO alojamientoDTO = alojamientoService.getById(id);
            return ResponseEntity.ok(alojamientoDTO);
        }
        catch (AlojamientoEliminadoException ex){
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO(ex.getMessage()));
        }catch(Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Ha ocurrido un error inesperado."));
        }
    }

    @PostMapping("alojamiento")
    @ResponseBody
    public ResponseEntity<ResponseHttp> saveOrUpdateAlojamiento(@RequestBody AlojamientoDTO alojamientoDTO){
        log.info("se crea alojamiento");
        try{
            alojamientoService.saveAlojamientoFromDTO(alojamientoDTO);
            return ResponseEntity.ok(alojamientoDTO);
        }catch(Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Ha ocurrido un error inesperado."));
        }
    }

    @PatchMapping("alojamiento")
    @ResponseBody
    public ResponseEntity<ResponseHttp> updateAlojamiento(@RequestBody AlojamientoDTO alojamientoDTO){
        return saveOrUpdateAlojamiento(alojamientoDTO);
    }

    @DeleteMapping("alojamiento/{id}")
    @ResponseBody
    public ResponseEntity<ResponseHttp> deleteAlojamiento(@PathVariable Long id) throws AlojamientoEliminadoException{
        log.info("Se quiere eliminar alojamiento de id: "+id);
        try{
            alojamientoService.deleteAlojamientoById(id);
            return ResponseEntity.ok(null);
        }catch(Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Ha ocurrido un error inesperado."));
        }
    }

    @GetMapping("alojamiento/estado/{estado}")
    @ResponseBody
    public ResponseEntity<List<AlojamientoDTO>> getPorEstados(@PathVariable String estado) {
    	log.info("Se piden todos los alojamientos con estado " + estado);
    	List<AlojamientoDTO> response = new ArrayList<AlojamientoDTO>();
    	switch (estado) {
    		case "PORVALIDAR":
    			response = alojamientoService.getAllPorValidar();
    			break;
    		case "VALIDADO":
    			response = alojamientoService.getAllValidados();
    			break;
    		case "RECHAZADO":
    			response = alojamientoService.getAllRechazados();
    			break;
    	}
    	return ResponseEntity.ok(response);
    }
    
    @PostMapping("/file/{id}")
    public ResponseEntity<ResponseHttp> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws Exception{
        try{
            CloudFileDTO cloudFileDTO = cloudFileController.internalUploadCloudFile(file);
            AlojamientoDTO alojamientoDTO = alojamientoService.addCloudFileToAlojamiento(cloudFileDTO, id);
            return ResponseEntity.ok(alojamientoDTO);
        }catch(Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Ha ocurrido un error inesperado."));
        }
    }

    @PostMapping("alojamiento/check/{id}")
    @ResponseBody
    public ResponseEntity<ResponseHttp> check(@PathVariable Long id){
        try{
            AlojamientoDTO alojamientoDTO = alojamientoService.checkAlojamiento(id);
            return ResponseEntity.ok(alojamientoDTO);
        }catch(Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Ha ocurrido un error inesperado."));
        }
    }

    @PostMapping("alojamiento/uncheck/{id}")
    @ResponseBody
    public ResponseEntity<ResponseHttp> uncheck(@PathVariable Long id,@NotNull @RequestParam("justificacion") String justificacion){
        try{
            AlojamientoDTO alojamientoDTO = alojamientoService.uncheckAlojamiento(id, justificacion);
            return ResponseEntity.ok(alojamientoDTO);
        }catch(Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Ha ocurrido un error inesperado."));
        }
    }

    @PutMapping("/pension")
    public ResponseEntity<ResponseHttp> agregarModificarPension(@RequestBody PensionDTO pensionDTO) throws AlojamientoEliminadoException{
        log.info("Se quiere agregar pension a alojamiento: "+pensionDTO.idalojamiento);
        try{
            AlojamientoDTO alojamientoDTO = alojamientoService.getById(pensionDTO.idalojamiento);
            if(alojamientoService.modificarPensionSiExiste(alojamientoDTO, pensionDTO)){
                //Refresh
                alojamientoDTO = alojamientoService.getById(pensionDTO.idalojamiento);
            }
            else{
                Pension pension = pensionService.createPension(pensionDTO);
                alojamientoDTO = alojamientoService.agregarPension(pensionDTO.idalojamiento, pension);
            }
            return ResponseEntity.ok(alojamientoDTO);
        }catch(Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Ha ocurrido un error inesperado."));
        }
    }

    @DeleteMapping("/pension/{id}")
    public ResponseEntity<ResponseHttp> agregarModificarPension(@PathVariable long id){
        try{
            AlojamientoDTO alojamientoDTO = alojamientoService.eliminarPension(id);
            pensionService.deletePension(id);
            return ResponseEntity.ok(alojamientoDTO);
        }catch(Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Ha ocurrido un error inesperado."));
        }
    }

}
