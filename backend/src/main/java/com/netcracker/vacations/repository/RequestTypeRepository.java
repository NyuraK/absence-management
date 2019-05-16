package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.RequestTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RequestTypeRepository extends CrudRepository<RequestTypeEntity, Integer> {

    List<RequestTypeEntity> findByName(String name);

}
