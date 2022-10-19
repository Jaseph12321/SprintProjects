package com.example.FirstProject.model.cashiEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;
import java.util.List;

public interface CashiRepository extends JpaRepository<Cashi, Integer>, JpaSpecificationExecutor<Cashi> {
    List<Cashi> findByCashiMgniId(String cashiId);

    @Transactional
    void deleteByCashiMgniId(String cashiId);

}
