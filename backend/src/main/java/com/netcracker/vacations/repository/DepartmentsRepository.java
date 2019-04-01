package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.DepartmentsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// hi
import java.util.ArrayList;
@Repository
public interface DepartmentsRepository extends CrudRepository<DepartmentsEntity, Integer> {
    ArrayList<DepartmentsEntity> findByDepartmentsId(Integer departmentsId);
    ArrayList<DepartmentsEntity> findByDirectorsId(Integer directorsId);
}