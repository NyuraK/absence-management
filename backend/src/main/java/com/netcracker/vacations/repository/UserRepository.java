package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    List<UserEntity> findByUsersId(Integer usersId);

    List<UserEntity> findByLogin(String login);

    List<UserEntity> findByRole(String role);

    List<UserEntity> findByRestDays(Integer restDays);

    List<UserEntity> findByHireDate(Date hireDate);

    List<UserEntity> findAllByTeam(TeamEntity team);

    List<UserEntity> findAllByTeam_TeamsId(Integer id);

    List<UserEntity> findByName(String name);

    List<UserEntity> findBySurname(String surname);

    List<UserEntity> findByFamilyName(String familyName);

    List<UserEntity> findByPhoneNumber(String phoneNumber);

    List<UserEntity> findByEmail(String email);

    List<UserEntity> findByDescription(String description);

    List<UserEntity> findByActivationCode(String activationCode);

    void deleteById(Integer usersId);

    void deleteByLogin(String login);
}