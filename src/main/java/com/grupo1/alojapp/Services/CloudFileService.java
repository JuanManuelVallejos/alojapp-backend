package com.grupo1.alojapp.Services;

import com.filestack.*;
import com.grupo1.alojapp.Assemblies.CloudFileAssembly;
import com.grupo1.alojapp.DTOs.CloudFileDTO;
import com.grupo1.alojapp.Exceptions.CloudFileNotFoundException;
import com.grupo1.alojapp.Exceptions.UploadCloudFileException;
import com.grupo1.alojapp.Model.CloudFile;
import com.grupo1.alojapp.Repositories.CloudFileRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudFileService {

    @Autowired
    private CloudFileRepository cloudFileRepository;
    @Autowired
    private AlojamientoService alojamientoService;

    private CloudFileAssembly cloudFileAssembly = new CloudFileAssembly();

    static Logger logger = Logger.getLogger(CloudFileService.class.getName());

    private String URL = "https://cdn.filestackcontent.com/";
    private String API_KEY = "AKtt8VgtWRbOSq8KW72cpz";

    public CloudFileDTO uploadCloudFile(MultipartFile mpFile) throws UploadCloudFileException {
        Policy.Builder policy = new Policy.Builder().giveFullAccess();
        Config CONFIG = new Config(API_KEY, policy.build(API_KEY));
        Client client = new Client(CONFIG);
        FileLink file;

        try {
            file = client.upload(mpFile.getInputStream(), (int)mpFile.getSize(), true);
        }catch(Exception exception){
            logger.error("Error al intentar uploadear file. MessageException: "+ exception.getMessage());
            throw new UploadCloudFileException("Error al intentar subir archivo a servidor");
        }

        String fileDownloadURI = URL+file.getHandle();

        CloudFile cloudFile = new CloudFile(mpFile.getName(),
                fileDownloadURI,mpFile.getContentType(),
                mpFile.getSize(), file.getHandle()
        );
        cloudFileRepository.save(cloudFile);

        CloudFileDTO cloudFileDTO = cloudFileAssembly.map(cloudFile, CloudFileDTO.class);

        return cloudFileDTO;
    }

    //TODO: Delete en server
    public void deleteCloudFile(long idCloudFile) throws CloudFileNotFoundException{
        CloudFile cloudFile;
        try{
            cloudFile = cloudFileRepository.getOne(idCloudFile);
        }catch(Exception ex){
            logger.error("No se encontro File de id: " + idCloudFile +" ." + ex.getMessage());
            throw new CloudFileNotFoundException("Archivo no encontrado de id: "+ idCloudFile);
        }
        alojamientoService.deleteFileFromAlojamiento(cloudFile);
        cloudFileRepository.delete(cloudFile);
    }

}
