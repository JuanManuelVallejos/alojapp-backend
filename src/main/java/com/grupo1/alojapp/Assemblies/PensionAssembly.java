package com.grupo1.alojapp.Assemblies;

import com.grupo1.alojapp.DTOs.PensionDTO;
import com.grupo1.alojapp.Model.Pension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collection;

public class PensionAssembly extends ModelMapper {

    public Collection<PensionDTO> map(Collection<Pension> pensiones){
        Collection<PensionDTO> pensionesDTO = new ArrayList<>();
        pensiones.forEach(pension ->
                pensionesDTO.add(super.map(pension, PensionDTO.class))
        );
        return pensionesDTO;
    }

}
