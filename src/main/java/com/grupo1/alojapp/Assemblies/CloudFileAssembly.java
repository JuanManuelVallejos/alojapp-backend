package com.grupo1.alojapp.Assemblies;

import com.grupo1.alojapp.DTOs.CloudFileDTO;
import com.grupo1.alojapp.Model.CloudFile;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collection;

public class CloudFileAssembly extends ModelMapper {

    public Collection<CloudFileDTO> map(Collection<CloudFile> cfs){
        Collection<CloudFileDTO> cloudFilesDTO = new ArrayList<>();
        cfs.forEach(cf ->
                cloudFilesDTO.add(super.map(cf, CloudFileDTO.class))
        );
        return cloudFilesDTO;
    }

}
