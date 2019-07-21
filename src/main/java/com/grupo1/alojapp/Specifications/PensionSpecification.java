package com.grupo1.alojapp.Specifications;

import com.grupo1.alojapp.Model.Pension;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PensionSpecification implements Specification<Pension> {

    private Pension filter;

    public PensionSpecification(Pension pension){
        super();
        this.filter = pension;
    }

    @Override
    public Predicate toPredicate(Root<Pension> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("tipopension"),filter.getTipopension());
    }

}
