package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.RequestEntity;
import com.netcracker.vacations.domain.RequestTypeEntity;
import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.domain.enums.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RequestRepository extends CrudRepository<RequestEntity, Integer> {
    List<RequestEntity> findByRequestsId(Integer requestsId);

    List<RequestEntity> findAllByUserLogin(String login);

    List<RequestEntity> findAllByUser(UserEntity user);

    List<RequestEntity> findAllByStatus(Status status);

    List<RequestEntity> findAllByTypeOfRequest(RequestTypeEntity typeOfRequest);

}
