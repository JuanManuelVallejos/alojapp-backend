package com.grupo1.alojapp.Specifications;

import com.grupo1.alojapp.Model.Rol;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RoleSpecification implements Specification<Rol> {

    private Rol filter;

    public RoleSpecification(Rol role){
        super();
        this.filter = role;
    }

    @Override
    public Predicate toPredicate(Root<Rol> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("name"),filter.getName());
    }
}
