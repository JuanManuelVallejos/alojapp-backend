package com.grupo1.alojapp.Services;

import com.grupo1.alojapp.Model.Rol;
import com.grupo1.alojapp.Repositories.RolRepository;
import com.grupo1.alojapp.Specifications.RoleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public Collection<Rol> getListRoleByNames(Collection<String> names){
        Collection roleCollection = new ArrayList();
        for(String name : names)
            roleCollection.add(getRoleByName(name));
        return  roleCollection;
    }

    public Rol getRoleByName(String name){
        Rol filter = new Rol();
        filter.setName(name);
        Specification<Rol> spec = new RoleSpecification(filter);
        return rolRepository.findOne(spec).get();
    }

}
