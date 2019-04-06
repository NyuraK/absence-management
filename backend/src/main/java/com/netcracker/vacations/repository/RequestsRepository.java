package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.RequestsEntity;
import com.netcracker.vacations.domain.TypeOfRequestsEntity;
import com.netcracker.vacations.domain.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
@Repository
@Transactional
public interface RequestsRepository extends CrudRepository<RequestsEntity, Integer> {
    ArrayList<RequestsEntity> findByRequestsId(Integer requestsId);
    ArrayList<RequestsEntity> findByUsersId(UsersEntity usersId);
    ArrayList<RequestsEntity> findByBeginning(Date beginning);
    ArrayList<RequestsEntity> findByEnding(Date ending);//Поиск в диапазоне см.
    ArrayList<RequestsEntity> findByStatus(String status);
    ArrayList<RequestsEntity> findAllByTypeOfRequest(TypeOfRequestsEntity typeOfRequest);
    ArrayList<RequestsEntity> findByDescription(String description);

    void deleteByRequestsId(Integer requestsId);
}
