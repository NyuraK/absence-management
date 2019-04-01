package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.DepartmentsEntity;
import com.netcracker.vacations.domain.TeamsEntity;
import com.netcracker.vacations.domain.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface TeamsRepository extends CrudRepository<TeamsEntity, Integer> {
    ArrayList<TeamsEntity> findByTeamsId(Integer teamsId);
    ArrayList<TeamsEntity> findByManagersId(UsersEntity managersId);
    ArrayList<TeamsEntity> findByRed(Integer red);
    ArrayList<TeamsEntity> findByGreen(Integer green);
    ArrayList<TeamsEntity> findByDepartmentsId(DepartmentsEntity departmentsId);
    ArrayList<TeamsEntity> findByName(String name);
}
