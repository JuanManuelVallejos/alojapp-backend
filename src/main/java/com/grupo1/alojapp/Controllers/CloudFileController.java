package com.grupo1.alojapp.Controllers;

import com.grupo1.alojapp.DTOs.CloudFileDTO;
import com.grupo1.alojapp.DTOs.ResponseDTO;
import com.grupo1.alojapp.DTOs.ResponseHttp;
import com.grupo1.alojapp.Exceptions.CloudFileNotFoundException;
import com.grupo1.alojapp.Exceptions.UploadCloudFileException;
import com.grupo1.alojapp.Services.CloudFileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT })
public class CloudFileController {

    @Autowired
    private CloudFileService cloudFileService;
    static Logger logger = Logger.getLogger(AlojamientoController.class.getName());

    @PostMapping("/cloud")
    public ResponseEntity<ResponseHttp> uploadFile(@RequestParam("file") MultipartFile file) {
        try{
            CloudFileDTO cloudFileDto = this.internalUploadCloudFile(file);
            return ResponseEntity.ok(cloudFileDto);
        }catch(UploadCloudFileException uploadException){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(
                            "Hubo un error al subir archivo al servidor externo"));
        }catch(Exception uploadException){
            logger.error(uploadException.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(
                            "Ha ocurrido un error inesperado."));
        }
    }

    public CloudFileDTO internalUploadCloudFile(MultipartFile file) throws Exception{
        return cloudFileService.uploadCloudFile(file);
    }

    @DeleteMapping("cloud/{idCloudFile:.+}")
    public ResponseEntity deleteFile(@PathVariable long idCloudFile) {
        logger.debug("Se elimina file de id: "+idCloudFile);
        try{
            cloudFileService.deleteCloudFile(idCloudFile);
        }catch(CloudFileNotFoundException cloudNotFoundException){
            return ResponseEntity.badRequest()
                    .body("El archivo que se quiere eliminar no existe");
        }
        catch(Exception ex){
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ha ocurrido un error inesperado.");
        }
        return ResponseEntity.ok(null);
    }

}
