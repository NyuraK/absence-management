package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.RequestEntity;
import com.netcracker.vacations.domain.RequestTypeEntity;
import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.domain.enums.Role;
import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.dto.RequestDTO;
import com.netcracker.vacations.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RequestService {

    private RequestRepository requestRepository;
    private RequestTypeRepository requestTypeRepository;
    private UserRepository userRepository;
    private TeamRepository teamRepository;
    private DepartmentRepository departmentRepository;
    private UserService userService;

    @Autowired
    public RequestService(RequestRepository requestRepository, RequestTypeRepository requestTypeRepository, UserRepository userRepository, TeamRepository teamRepository, DepartmentRepository departmentRepository, UserService userService) {
        this.requestRepository = requestRepository;
        this.requestTypeRepository = requestTypeRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.departmentRepository = departmentRepository;
        this.userService = userService;
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
        if (user.getRole().equals(Role.ADMIN)) {
            for (RequestEntity entity : requestRepository.findAll()) {
                if ((entity.getStatus().equals(Status.CONSIDER.getName()))) {
                    response.add(toDTO(entity));
                }
            }
        } else if (user.getRole().equals(Role.DIRECTOR)) {
            List<TeamEntity> directorsTeams = teamRepository.findAllByDepartment(departmentRepository.findByDirector(user).get(0));
            for (RequestEntity entity : requestRepository.findAll()) {
                for (TeamEntity team : directorsTeams) {
                    if ((entity.getStatus().equals(Status.CONSIDER.getName())) && (team.equals(entity.getUser().getTeam()))) {
                        response.add(toDTO(entity));
                        break;
                    }
                }
            }
        } else {
            List<TeamEntity> managersTeams = teamRepository.findAllByManager(user);
            for (RequestEntity entity : requestRepository.findAll()) {
                for (TeamEntity team : managersTeams) {
                    if ((entity.getStatus().equals(Status.CONSIDER.getName())) && (team.equals(entity.getUser().getTeam()))) {
                        response.add(toDTO(entity));
                        break;
                    }
                }
            }
        }
        return response;
    }

    public List<RequestDTO> getResolvedRequests(String name) {
        List<RequestDTO> response = new ArrayList<>();
        UserEntity user = userRepository.findByLogin(name).get(0);
        if (user.getRole().equals(Role.ADMIN)) {
            for (RequestEntity entity : requestRepository.findAll()) {
                if (!entity.getStatus().equals(Status.CONSIDER.getName())) {
                    response.add(toDTO(entity));
                }
            }
        } else if (user.getRole().equals(Role.DIRECTOR)) {
            List<TeamEntity> directorsTeams = teamRepository.findAllByDepartment(departmentRepository.findByDirector(user).get(0));
            for (RequestEntity entity : requestRepository.findAll()) {
                for (TeamEntity team : directorsTeams) {
                    if ((!entity.getTypeOfRequest().getNeedApproval()
                            || !entity.getStatus().equals(Status.CONSIDER.getName())) && (team.equals(entity.getUser().getTeam()))) {
                        response.add(toDTO(entity));
                        break;
                    }
                }
            }
        } else {
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
        }
        return response;
    }

    private RequestDTO toDTO(RequestEntity entity) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(entity.getRequestsId());
        String name = entity.getUser().getName();
        String familyName = entity.getUser().getFamilyName();
        if ((name.isEmpty() || name == null) && (familyName.isEmpty()) || familyName == null) {
            requestDTO.setName("-");
        } else if (familyName.isEmpty() || familyName == null) {
            requestDTO.setName(entity.getUser().getName());
        } else if (name.isEmpty() || name == null) {
            requestDTO.setName(entity.getUser().getFamilyName());
        } else {
            requestDTO.setName(entity.getUser().getName() + " " + entity.getUser().getFamilyName());
        }
        if (entity.getUser().getTeam() != null) {
            requestDTO.setTeamName(entity.getUser().getTeam().getName());
        } else {
            requestDTO.setTeamName("-");
        }
        if (entity.getUser().getTeam().getName() != null) {
            requestDTO.setTeamName(entity.getUser().getTeam().getName());
        } else {
            requestDTO.setTeamName("-");
        }
        requestDTO.setName(entity.getUser().getName() + " " + entity.getUser().getFamilyName());
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

    public boolean isManagerOnRest(String login) {
        boolean answer = false;
        UserEntity user = userRepository.findByLogin(login).get(0);
        if (user.getTeam() != null) {
            UserEntity manager = user.getTeam().getManager();
            List<RequestEntity> requests = requestRepository.findAllByUser(manager);
            Date currentDate = new Date();
            Calendar calCurrent = Calendar.getInstance();
            Calendar calBegin = Calendar.getInstance();
            Calendar calEnd = Calendar.getInstance();

            calCurrent.setTime(currentDate);

            boolean sameDayBegin = calCurrent.get(Calendar.DAY_OF_YEAR) == calBegin.get(Calendar.DAY_OF_YEAR) && calCurrent.get(Calendar.YEAR) == calBegin.get(Calendar.YEAR);
            boolean sameDayEnd = calCurrent.get(Calendar.DAY_OF_YEAR) == calEnd.get(Calendar.DAY_OF_YEAR) && calCurrent.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR);
            for (RequestEntity req : requests) {
                calBegin.setTime(req.getBeginning());
                calEnd.setTime(req.getEnding());
                if ((((req.getBeginning()).before(currentDate)) || sameDayBegin) && ((((req.getEnding()).after(currentDate)) || sameDayEnd))) {
                    if (req.getTypeOfRequest().getInfluenceOnVr()) {
                        answer = true;
                        break;
                    }
                }
            }
        }
        return answer;
    }

    public void sendMailRequest(RequestDTO request) {
        UserEntity user = userRepository.findByLogin(request.getUsername()).get(0);
        boolean needToSend = requestTypeRepository.findByName(request.getType()).get(0).getNeedApproval();
        if (needToSend && user.getTeam() != null) {
            UserEntity director = user.getTeam().getDepartment().getDirector();
            if (director.getEmail() != null) {
                String message = String.format("Dear " + director.getName() + " " + director.getSurname() + ".\n" + request.getDescription() +
                        "||Request was sent by " + user.getName() + " " + user.getSurname() + ". Reason: " + request.getType() + " Begin date: " + request.getStart() + ". End date: " + request.getEnd() + ". " +
                        "Created on " + request.getCreation() + ". ||");
                userService.send(director.getEmail(), "Request by " + user.getName() + " " + user.getSurname() + ".", message);
            }
        }
    }
}
