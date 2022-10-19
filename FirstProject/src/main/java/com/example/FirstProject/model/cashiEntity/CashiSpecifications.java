package com.example.FirstProject.model.cashiEntity;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class CashiSpecifications {
    public static Specification<Cashi> likeYear(String year) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(Cashi_.CASHI_MGNI_ID), "%" + year + "%");
            return p;
        };
    }

}
