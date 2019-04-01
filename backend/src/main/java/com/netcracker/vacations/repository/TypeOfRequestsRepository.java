package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.TypeOfRequestsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface TypeOfRequestsRepository extends CrudRepository<TypeOfRequestsEntity, Integer>{
    ArrayList<TypeOfRequestsEntity> findByTypeOfRequest(Integer typeOfRequest);
    ArrayList<TypeOfRequestsEntity> findByInfluenceOnVr(Boolean influenceOnVr);
    ArrayList<TypeOfRequestsEntity> findByNeedApproval(Boolean needApproval);
    ArrayList<TypeOfRequestsEntity> findByName(String name);
}
