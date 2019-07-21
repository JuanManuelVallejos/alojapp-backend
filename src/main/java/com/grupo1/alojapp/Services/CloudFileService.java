package com.grupo1.alojapp.Services;

import com.filestack.*;
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

    static Logger logger = Logger.getLogger(CloudFileService.class.getName());

    private String URL = "https://cdn.filestackcontent.com/";
    private String API_KEY = "AKtt8VgtWRbOSq8KW72cpz";

    public CloudFile uploadCloudFile(MultipartFile mpFile) throws Exception{
        Policy.Builder policy = new Policy.Builder().giveFullAccess();
        Config CONFIG = new Config(API_KEY, policy.build(API_KEY));
        Client client = new Client(CONFIG);

        try {
            FileLink file = client.upload(mpFile.getInputStream(), (int)mpFile.getSize(), true);
            String fileDownloadURI = URL+file.getHandle();

            CloudFile cloudFile = new CloudFile(mpFile.getName(),
                    fileDownloadURI,mpFile.getContentType(),
                    mpFile.getSize(), file.getHandle()
            );

            cloudFileRepository.save(cloudFile);

            return cloudFile;
        }catch(Exception exception){
            logger.error("Error al intentar uploadear file. InnerException: "+ exception.getStackTrace());
            throw exception;
        }
    }

    //TODO: Delete en server
    public void deleteCloudFile(long idCloudFile){
        CloudFile cloudFile = cloudFileRepository.getOne(idCloudFile);
        alojamientoService.deleteFileFromAlojamiento(cloudFile);
        cloudFileRepository.delete(cloudFile);
    }

}
