package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.DepartmentsEntity;
import com.netcracker.vacations.domain.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.ArrayList;
@Repository
@Transactional
public interface DepartmentsRepository extends CrudRepository<DepartmentsEntity, Integer> {
    ArrayList<DepartmentsEntity> findByDepartmentsId(Integer departmentsId);
    ArrayList<DepartmentsEntity> findByDirectorsId(UsersEntity directorsId);
    ArrayList<DepartmentsEntity> findAllByDirectorsId(UsersEntity directorsId);

    void deleteByDepartmentsId(Integer departmentsId);
}