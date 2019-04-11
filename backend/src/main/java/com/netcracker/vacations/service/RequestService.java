package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.RequestEntity;
import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.dto.RequestDTO;
import com.netcracker.vacations.repository.RequestRepository;
import com.netcracker.vacations.repository.RequestTypeRepository;
import com.netcracker.vacations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class RequestService {

    private RequestRepository requestRepository;
    private RequestTypeRepository requestTypeRepository;
    private UserRepository userRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository, RequestTypeRepository requestTypeRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.requestTypeRepository = requestTypeRepository;
        this.userRepository = userRepository;
    }

    public void saveRequest(RequestDTO request) {
        RequestEntity requestEntity = new RequestEntity(
                userRepository.findByLogin("Denis").get(0),
                Date.valueOf("2019-04-09"),
                Date.valueOf("2019-04-18"),
                requestTypeRepository.findByName(request.getType().name).get(0),
                Status.CONSIDER

        );
//        requestEntity.setUsersId(userRepository.findById(1).get());
//        requestEntity.setBeginning(Date.valueOf("2019-04-01"));
//        requestEntity.setEnding(Date.valueOf("2019-04-21"));
//        requestEntity.setTypeOfRequest(requestTypeRepository.findByName(request.getType().name).get(0));
//        requestEntity.setStatus(Status.CONSIDER);
        requestEntity.setDescription("Off to Moscow");

//        request.setStatus(Status.CONSIDER);
//        BeanUtils.copyProperties(request, requestEntity);
        requestRepository.save(requestEntity);
    }


}
