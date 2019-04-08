package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.RequestTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public interface RequestTypeRepository extends CrudRepository<RequestTypeEntity, Integer>{
    List<RequestTypeEntity> findByTypeOfRequest(Integer typeOfRequest);
    List<RequestTypeEntity> findByInfluenceOnVr(Boolean influenceOnVr);
    List<RequestTypeEntity> findByNeedApproval(Boolean needApproval);
    List<RequestTypeEntity> findByName(String name);

    void deleteByTypeOfRequest(Integer typeOfRequest);
}
