package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    List<UserEntity> findByUsersId(Integer usersId);

    List<UserEntity> findByLogin(String login);

    List<UserEntity> findAllByTeam(TeamEntity team);

    List<UserEntity> findAllByTeamTeamsId(Integer id);

    List<UserEntity> findAllByTeamManagerUsersId(Integer id);

    List<UserEntity> findByEmail(String email);

    List<UserEntity> findByActivationCode(String activationCode);

    void deleteById(Integer usersId);

}