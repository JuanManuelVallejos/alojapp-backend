package com.grupo1.alojapp.Controllers;

import com.grupo1.alojapp.Model.CloudFile;
import com.grupo1.alojapp.Services.CloudFileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT })
public class CloudFileController {

    @Autowired
    private CloudFileService cloudFileService;
    static Logger logger = Logger.getLogger(AlojamientoController.class.getName());

    @PostMapping("/cloud/uploadFile")
    public CloudFile uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        CloudFile cloudFile = cloudFileService.uploadCloudFile(file);
        return cloudFile;
    }

    @PutMapping("cloud/deleteFile/{idCloudFile:.+}")
    public ResponseEntity deleteFile(@PathVariable long idCloudFile) {
        logger.debug("Se elimina file de id: "+idCloudFile);
        cloudFileService.deleteCloudFile(idCloudFile);
        return ResponseEntity.ok(null);
    }

}
