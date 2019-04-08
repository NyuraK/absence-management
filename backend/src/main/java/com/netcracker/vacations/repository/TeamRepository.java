package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.DepartmentEntity;
import com.netcracker.vacations.domain.TeamsEntity;
import com.netcracker.vacations.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public interface TeamRepository extends CrudRepository<TeamsEntity, Integer> {
    List<TeamsEntity> findByTeamsId(Integer teamsId);
    List<TeamsEntity> findByManagersId(UserEntity managersId);
    List<TeamsEntity> findAllByManagersId(UserEntity managersId);
    List<TeamsEntity> findByQuota(Integer quota);
    List<TeamsEntity> findByDepartmentsId(DepartmentEntity departmentsId);
    List<TeamsEntity> findAllByDepartmentsId(DepartmentEntity departmentsId);
    List<TeamsEntity> findByName(String name);

    void deleteByTeamsId(Integer teamsId);
    void deleteByName(String name);
}
