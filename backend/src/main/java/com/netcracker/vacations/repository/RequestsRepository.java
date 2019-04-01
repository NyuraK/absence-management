package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.RequestsEntity;
import com.netcracker.vacations.domain.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
@Repository
public interface RequestsRepository extends CrudRepository<RequestsEntity, Integer> {
    ArrayList<RequestsEntity> findByRequestsId(Integer requestsId);
    ArrayList<RequestsEntity> findByUsersId(UsersEntity usersId);
    ArrayList<RequestsEntity> findByBeginning(Date beginning);
    ArrayList<RequestsEntity> findByEnding(Date ending);//Поиск в диапазоне см.
    ArrayList<RequestsEntity> findByStatus(String status);
    ArrayList<RequestsEntity> findByDescription(String description);
}
