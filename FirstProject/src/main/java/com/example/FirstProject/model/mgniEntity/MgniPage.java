package com.example.FirstProject.model.mgniEntity;

import com.example.FirstProject.dto.request.Conditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class MgniPage {

    private String startDate;
    private String endDate;
    private String cmno;
    private String bankno;
    private String ccy;
    private String itype;
    private int page;
    private int size;

    @Autowired
    MgniRepository mgniRepository;


    public Page<Mgni> thePage(Conditions conditions) {
        this.startDate = conditions.getStartDate();
        this.endDate = conditions.getEndDate();
        this.cmno = conditions.getCmno();
        this.bankno = conditions.getBankno();
        this.ccy = conditions.getCcy();
        this.itype = conditions.getItype();
        this.page = conditions.getPage();
        this.size = conditions.getSize();
        return SearchSpec();
    }

    private Page<Mgni> SearchSpec() {
        Specification<Mgni> specification = new Specification<Mgni>() {
            @Override
            public Predicate toPredicate(Root<Mgni> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicateList = new ArrayList<>();
                if ("".equals(cmno)) {
                    predicateList.add(cb.equal(root.get(Mgni_.MGNI_CM_NO), cmno));
                }

                if ("".equals(ccy)) {
                    predicateList.add(cb.equal(root.get(Mgni_.MGNI_CCY), ccy));
                }

                if ("".equals(bankno)) {
                    predicateList.add(cb.equal(root.get(Mgni_.MGNI_BANK_NO), bankno));
                }

                if ("".equals(itype)) {
                    predicateList.add(cb.equal(root.get(Mgni_.MGNI_ITYPE), itype));
                }

                query.orderBy(cb.asc(root.get(Mgni_.MGNI_ID)));
                Predicate[] predicates = new Predicate[predicateList.size()];
                return cb.and(predicateList.toArray(predicates));
            }
        };
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.ASC, Mgni_.MGNI_ID);
        return this.mgniRepository.findAll(specification, pageRequest);
    }
}
