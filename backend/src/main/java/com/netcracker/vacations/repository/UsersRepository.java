package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.TeamsEntity;
import com.netcracker.vacations.domain.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
@Repository
@Transactional
public interface UsersRepository extends CrudRepository<UsersEntity, Integer> {
    ArrayList<UsersEntity> findByUsersId(Integer usersId);
    ArrayList<UsersEntity> findByLogin(String login);
    ArrayList<UsersEntity> findByPassword(String password);
    ArrayList<UsersEntity> findByRole(String role);
    ArrayList<UsersEntity> findByRestDays(Integer restDays);
    ArrayList<UsersEntity> findByHireDate(Date hireDate);
    ArrayList<UsersEntity> findAllByTeamsId(TeamsEntity teamsId);
    ArrayList<UsersEntity> findByName(String name);
    ArrayList<UsersEntity> findBySurname(String surname);
    ArrayList<UsersEntity> findByFamilyName(String familyName);
    ArrayList<UsersEntity> findByPhoneNumber(String phoneNumber);
    ArrayList<UsersEntity> findByEmail(String email);
    ArrayList<UsersEntity> findByDescription(String description);

    void deleteById(Integer usersId);
    void deleteByLogin(String login);
}