package com.grupo1.alojapp.Controllers;

import com.grupo1.alojapp.DTOs.AlojamientoDTO;
import com.grupo1.alojapp.DTOs.CloudFileDTO;
import com.grupo1.alojapp.DTOs.DeletePensionDTO;
import com.grupo1.alojapp.DTOs.PensionDTO;
import com.grupo1.alojapp.Exceptions.AlojamientoEliminadoException;
import com.grupo1.alojapp.Model.CloudFile;
import com.grupo1.alojapp.Model.Pension;
import com.grupo1.alojapp.Model.TIPOPENSION;
import com.grupo1.alojapp.Services.AlojamientoService;
import com.grupo1.alojapp.Services.PensionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT })
public class AlojamientoController {

    static Logger log = Logger.getLogger(AlojamientoController.class.getName());

    @Autowired
    private AlojamientoService alojamientoService;
    @Autowired
    private PensionService pensionService;
    @Autowired
    private CloudFileController cloudFileController;

    @GetMapping("alojamiento/get")
    @ResponseBody
    public ResponseEntity<List<AlojamientoDTO>> getAlojamientosVigentes(){
        log.debug("Se piden todos los alojamientos");
        List<AlojamientoDTO> alojamientosDTO = alojamientoService.getAllVigentes();
        return ResponseEntity.ok(alojamientosDTO);
    }

    @GetMapping("alojamiento/get/{id}")
    @ResponseBody
    public ResponseEntity<AlojamientoDTO> getAlojamiento(@PathVariable Long id) throws AlojamientoEliminadoException {
        AlojamientoDTO alojamientoDTO = alojamientoService.getById(id);
        return ResponseEntity.ok(alojamientoDTO);
    }

    @PostMapping("alojamiento/create")
    @ResponseBody
    public ResponseEntity<AlojamientoDTO> saveOrUpdateAlojamiento(@RequestBody AlojamientoDTO alojamientoDTO){
        alojamientoService.saveAlojamientoFromDTO(alojamientoDTO);
        return ResponseEntity.ok(alojamientoDTO);
    }

    @PostMapping("alojamiento/update")
    @ResponseBody
    public ResponseEntity<AlojamientoDTO> updateAlojamiento(@RequestBody AlojamientoDTO alojamientoDTO){
        return saveOrUpdateAlojamiento(alojamientoDTO);
    }

    @PutMapping("alojamiento/delete/{id}")
    @ResponseBody
    public void deleteAlojamiento(@PathVariable Long id) throws AlojamientoEliminadoException{
        alojamientoService.deleteAlojamientoById(id);
    }

    @PostMapping("/alojamiento/uploadFile/{id}")
    public ResponseEntity<AlojamientoDTO> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws Exception{
        CloudFile cloudFile = cloudFileController.uploadFile(file);
        AlojamientoDTO alojamientoDTO = alojamientoService.addCloudFileToAlojamiento(cloudFile,id);
        return ResponseEntity.ok(alojamientoDTO);
    }

    @PostMapping("/alojamiento/check/{id}")
    public ResponseEntity<AlojamientoDTO> check(@PathVariable Long id){
        AlojamientoDTO alojamientoDTO = alojamientoService.checkAlojamiento(id);
        return ResponseEntity.ok(alojamientoDTO);
    }

    @PostMapping("/alojamiento/uncheck/{id}")
    public ResponseEntity<AlojamientoDTO> uncheck(@PathVariable Long id,@NotNull @RequestParam("justificacion") String justificacionRechazo){
        AlojamientoDTO alojamientoDTO = alojamientoService.uncheckAlojamiento(id, justificacionRechazo);
        return ResponseEntity.ok(alojamientoDTO);
    }

    @PostMapping("/alojamiento/agregarModificarPension/{id}")
    public ResponseEntity<AlojamientoDTO> agregarModificarPension(@PathVariable Long id, @RequestBody PensionDTO pensionDTO){
        Pension pension = pensionService.createPension(pensionDTO);
        AlojamientoDTO alojamientoDTO = alojamientoService.agregarPension(id, pension);
        return ResponseEntity.ok(alojamientoDTO);
    }

    @PostMapping("/alojamiento/deletePension")
    public ResponseEntity<AlojamientoDTO> agregarModificarPension(@RequestBody DeletePensionDTO pensionDTO){
        AlojamientoDTO alojamientoDTO = alojamientoService.eliminarPension(pensionDTO);
        pensionService.deletePension(pensionDTO);
        return ResponseEntity.ok(alojamientoDTO);
    }

}
