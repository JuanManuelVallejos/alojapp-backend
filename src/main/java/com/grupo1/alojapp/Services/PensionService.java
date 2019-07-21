package com.grupo1.alojapp.Services;

import com.grupo1.alojapp.DTOs.DeletePensionDTO;
import com.grupo1.alojapp.DTOs.PensionDTO;
import com.grupo1.alojapp.Model.Pension;
import com.grupo1.alojapp.Model.TIPOPENSION;
import com.grupo1.alojapp.Repositories.PensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PensionService {

    @Autowired
    private PensionRepository pensionRepository;

    public Pension getPension(long idPension){
        return pensionRepository.getOne(idPension);
    }

    public Pension createPension(PensionDTO pensionDTO){
        Pension pension = new Pension();
        pension.setTipopension(pensionDTO.getTipopension());
        pension.setPrecio(pensionDTO.getPrecio());
        pensionRepository.save(pension);
        return pension;
    }

    public Pension deletePension(DeletePensionDTO pensionDTO){
        Pension pension = pensionRepository.getOne(pensionDTO.getIdPension());
        pensionRepository.delete(pension);
        return pension;
    }

}
