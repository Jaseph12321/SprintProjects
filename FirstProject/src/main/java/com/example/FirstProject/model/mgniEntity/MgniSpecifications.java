package com.example.FirstProject.model.mgniEntity;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

public class MgniSpecifications {

    public static Specification<Mgni> betweenTime(LocalDateTime startTime, LocalDateTime endTime) {
        return (root, query, cb) -> {
            Predicate p = cb.between(root.get(Mgni_.MGNI_TIME), startTime, endTime);

            return p;
        };
    }

    public static Specification<Mgni> equalsCcy(String ccy) {
        return (root, query, cb) -> {
            return cb.equal(root.get(Mgni_.MGNI_CCY), ccy);
        };
    }

    public static Specification<Mgni> equalsBankNo(String bankno) {
        return (root, query, cb) -> {
            return cb.equal(root.get(Mgni_.MGNI_CCY), bankno);
        };
    }

    public static Specification<Mgni> equalsCmno(String cmno) {
        return (root, query, cb) -> {
            return cb.equal(root.get(Mgni_.MGNI_CCY), cmno);
        };
    }

    public static Specification<Mgni> equalsItype(String type) {
        return (root, query, cb) -> {
            return cb.equal(root.get(Mgni_.MGNI_ITYPE), type);
        };
    }
}
