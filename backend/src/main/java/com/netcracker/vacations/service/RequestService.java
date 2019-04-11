package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.RequestEntity;
import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.dto.RequestDTO;
import com.netcracker.vacations.repository.RequestRepository;
import com.netcracker.vacations.repository.RequestTypeRepository;
import com.netcracker.vacations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                userRepository.findByLogin(request.getUsername()).get(0),
                request.getStart(),
                request.getEnd(),
                requestTypeRepository.findByName(request.getType().name).get(0),
                Status.CONSIDER
        );
        requestEntity.setDescription(request.getDescription());
//        BeanUtils.copyProperties(request, requestEntity);
        requestRepository.save(requestEntity);
    }


}
