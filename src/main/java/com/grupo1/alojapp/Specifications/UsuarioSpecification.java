package com.grupo1.alojapp.Specifications;

import com.grupo1.alojapp.Model.Usuario;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UsuarioSpecification implements Specification<Usuario> {

    private Usuario filter;

    public UsuarioSpecification(Usuario role){
        super();
        this.filter = role;
    }

    @Override
    public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("email"),filter.getEmail());
    }
}