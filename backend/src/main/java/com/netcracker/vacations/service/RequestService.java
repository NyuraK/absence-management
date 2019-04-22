package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.RequestEntity;
import com.netcracker.vacations.domain.RequestTypeEntity;
import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.dto.RequestDTO;
import com.netcracker.vacations.repository.RequestRepository;
import com.netcracker.vacations.repository.RequestTypeRepository;
import com.netcracker.vacations.repository.TeamRepository;
import com.netcracker.vacations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RequestService {

    private RequestRepository requestRepository;
    private RequestTypeRepository requestTypeRepository;
    private UserRepository userRepository;
    private TeamRepository teamRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository, RequestTypeRepository requestTypeRepository, UserRepository userRepository, TeamRepository teamRepository) {
        this.requestRepository = requestRepository;
        this.requestTypeRepository = requestTypeRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    public void saveRequest(RequestDTO request) {
        RequestTypeEntity type = requestTypeRepository.findByName(request.getType()).get(0);
        Status status = Status.CONSIDER;
        if (!type.getNeedApproval()) {
            status = Status.ACCEPTED;
        }
        RequestEntity requestEntity = new RequestEntity(
                userRepository.findByLogin(request.getUsername()).get(0),
                request.getStart(),
                request.getEnd(),
                type,
                status
        );
        requestEntity.setDescription(request.getDescription());
        requestRepository.save(requestEntity);
    }


    public void updateRequest(Status status, List<Integer> requests) {
        //TODO add logic to decrement amount of vacant days left
        for (Integer id : requests) {
            RequestEntity entity = requestRepository.findById(id).get();
            entity.setStatus(status);
            requestRepository.save(entity);
        }
    }

    public List<RequestDTO> getActiveRequests(String name) {
        List<RequestDTO> response = new ArrayList<>();
        UserEntity user = userRepository.findByLogin(name).get(0);
        /*System.out.println("NAMED"+((user.getRole().equals("Administrator"))||(user.getRole().equals("Director"))));
        if ((user.getRole().equals("Administrator"))||(user.getRole().equals("Director"))){
            for (RequestEntity entity : requestRepository.findAll()) {
                    if ((entity.getStatus().equals(Status.CONSIDER.getName()))) {
                        response.add(toDTO(entity));
                    }
            }
        } else {*/
        List<TeamEntity> managersTeams = teamRepository.findAllByManager(user);
        for (RequestEntity entity : requestRepository.findAll()) {
            for (TeamEntity team : managersTeams) {
                if ((entity.getStatus().equals(Status.CONSIDER.getName())) && (team.equals(entity.getUser().getTeam()))) {
                    response.add(toDTO(entity));
                    break;
                }
            }
        }
        //}
        return response;
    }

    public List<RequestDTO> getResolvedRequests(String name) {
        List<RequestDTO> response = new ArrayList<>();
        UserEntity user = userRepository.findByLogin(name).get(0);
        List<TeamEntity> managersTeams = teamRepository.findAllByManager(user);
        for (RequestEntity entity : requestRepository.findAll()) {
            for (TeamEntity team : managersTeams) {
                if ((!entity.getTypeOfRequest().getNeedApproval()
                        || !entity.getStatus().equals(Status.CONSIDER.getName())) && (team.equals(entity.getUser().getTeam()))) {
                    response.add(toDTO(entity));
                    break;
                }
            }
        }
        return response;
    }

    private RequestDTO toDTO(RequestEntity entity) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(entity.getRequestsId());
        if ((entity.getUser().getName() == null) && (entity.getUser().getFamilyName() == null)) {
            requestDTO.setName("-" + " " + "-");
        } else if (entity.getUser().getFamilyName() == null) {
            requestDTO.setName(entity.getUser().getName() + " " + "-");
        } else if (entity.getUser().getName() == null) {
            requestDTO.setName("-" + " " + entity.getUser().getFamilyName());
        } else {
            requestDTO.setName(entity.getUser().getName() + " " + entity.getUser().getFamilyName());
        }
        //if (entity.getUser().getTeamsId().getName() != null) {
        requestDTO.setTeamname(entity.getUser().getTeam().getName());
        // } else {
        //requestDTO.setTeamname("-");
        //}
        requestDTO.setDescription(entity.getDescription());
        requestDTO.setStart(entity.getBeginning());
        requestDTO.setEnd(entity.getEnding());
        requestDTO.setType(entity.getTypeOfRequest().getName());
        requestDTO.setStatus(entity.getStatus());
        return requestDTO;
    }

    public List<List<String>> getRequests() {
        List<List<String>> response = new ArrayList<>();
        for (RequestEntity entity : requestRepository.findAll()) {
            if (entity.getStatus().equals(Status.ACCEPTED.getName()))
                response.add(toTimelineDTO(entity));
        }
        return response;
    }

    private List<String> toTimelineDTO(RequestEntity entity) {
        List<String> res = new ArrayList<>();
        res.add(entity.getUser().getName() + " " + entity.getUser().getFamilyName());
        res.add(entity.getTypeOfRequest().getName());
        res.add(entity.getBeginning().toString());
        res.add(entity.getEnding().toString());
        return res;
    }

}
