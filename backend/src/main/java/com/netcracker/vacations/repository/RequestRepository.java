package com.netcracker.vacations.repository;

import com.netcracker.vacations.domain.RequestEntity;
import com.netcracker.vacations.domain.RequestTypeEntity;
import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.domain.enums.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface RequestRepository extends CrudRepository<RequestEntity, Integer> {
    List<RequestEntity> findByRequestsId(Integer requestsId);

    List<RequestEntity> findByUser(UserEntity user);

    List<RequestEntity> findAllByUser(UserEntity user);

    List<RequestEntity> findByBeginning(Date beginning);

    List<RequestEntity> findAllByBeginning(Date beginning);

    List<RequestEntity> findByEnding(Date ending);

    List<RequestEntity> findAllByEnding(Date ending);//Поиск в диапазоне см.

    List<RequestEntity> findByStatus(Status status);

    List<RequestEntity> findAllByStatus(Status status);

    List<RequestEntity> findAllByTypeOfRequest(RequestTypeEntity typeOfRequest);

    List<RequestEntity> findByDescription(String description);

    void deleteByRequestsId(Integer requestsId);
}
