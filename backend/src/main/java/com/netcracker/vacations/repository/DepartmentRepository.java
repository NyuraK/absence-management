package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.DepartmentEntity;
import com.netcracker.vacations.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Integer> {
    List<DepartmentEntity> findByDepartmentsId(Integer departmentsId);

    List<DepartmentEntity> findByDirector(UserEntity director);

    List<DepartmentEntity> findAllByDirector(UserEntity director);

    void deleteByDepartmentsId(Integer departmentsId);
}