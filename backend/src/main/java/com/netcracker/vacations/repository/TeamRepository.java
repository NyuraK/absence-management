package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.DepartmentEntity;
import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TeamRepository extends CrudRepository<TeamEntity, Integer> {
    List<TeamEntity> findByTeamsId(Integer teamsId);

    List<TeamEntity> findByManager(UserEntity manager);

    List<TeamEntity> findAllByManager(UserEntity manager);

    List<TeamEntity> findByQuota(Integer quota);

    List<TeamEntity> findByDepartment(DepartmentEntity department);

    List<TeamEntity> findAllByDepartment(DepartmentEntity department);

    List<TeamEntity> findByName(String name);

    void deleteByTeamsId(Integer teamsId);

    void deleteByName(String name);
}
