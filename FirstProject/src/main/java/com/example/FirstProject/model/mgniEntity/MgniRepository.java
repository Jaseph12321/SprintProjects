package com.example.FirstProject.model.mgniEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MgniRepository extends JpaRepository<Mgni, Integer>, JpaSpecificationExecutor<Mgni> {
    Mgni findByMgniId(String id);

}
