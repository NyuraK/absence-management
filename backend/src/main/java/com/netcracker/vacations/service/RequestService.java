package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.RequestEntity;
import com.netcracker.vacations.domain.RequestTypeEntity;
import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.domain.enums.RequestType;
import com.netcracker.vacations.domain.enums.Role;
import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.dto.RequestDTO;
import com.netcracker.vacations.exception.TooManyDaysException;
import com.netcracker.vacations.repository.*;
import com.netcracker.vacations.security.SecurityExpressionMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@Transactional
public class RequestService {
    private static final Logger logger = LogManager.getLogger(RequestService.class);

    private RequestRepository requestRepository;
    private RequestTypeRepository requestTypeRepository;
    private UserRepository userRepository;
    private TeamRepository teamRepository;
    private DepartmentRepository departmentRepository;
    private UserService userService;
    private MethodsService methodService;

    @Autowired
    public RequestService(RequestRepository requestRepository, RequestTypeRepository requestTypeRepository, UserRepository userRepository, TeamRepository teamRepository, DepartmentRepository departmentRepository, UserService userService, MethodsService methodService) {
        this.requestRepository = requestRepository;
        this.requestTypeRepository = requestTypeRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.departmentRepository = departmentRepository;
        this.userService = userService;
        this.methodService = methodService;
    }

    public void saveRequest(RequestDTO request) {
        RequestTypeEntity type = requestTypeRepository.findByName(request.getType()).get(0);
        Status status = Status.CONSIDER;
        if (!type.getNeedApproval()) {
            status = Status.ACCEPTED;
        }

        UserEntity user = userRepository.findByLogin(request.getUsername()).get(0);
        Date begin = prepareDate(request.getStart());
        Date end = prepareDate(request.getEnd());
        if (type.getName().equals(RequestType.VACATION.getName())) {
            long diffInMillies = Math.abs(begin.getTime() - end.getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diffInDays > user.getRestDays()) {
                throw new TooManyDaysException("You took too many days. You have only " + user.getRestDays() + " vacant days");
            }
        }

        RequestEntity requestEntity = new RequestEntity(user, begin, end, type, status);

        requestEntity.setDescription(request.getDescription());
        requestRepository.save(requestEntity);
    }


    @PreAuthorize("@Security.isTeamMember(#username, null)")
    public void updateRequest(Status status, List<Integer> requests, @P("username") String username) {
        for (Integer id : requests) {
            RequestEntity entity = requestRepository.findById(id).get();
            entity.setStatus(status);
            if (status.equals(Status.ACCEPTED)
                    && entity.getTypeOfRequest().getName().equals(RequestType.VACATION.getName()))
                decrementRestDays(entity);
            requestRepository.save(entity);
        }
    }

    private void decrementRestDays(RequestEntity entity) {
        UserEntity user = entity.getUser();
        Date begin = entity.getBeginning();
        Date end = entity.getEnding();
        long diffInMillis = Math.abs(begin.getTime() - end.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        Integer restDays = user.getRestDays();
        user.setRestDays(restDays - (int) diffInDays);
        userRepository.save(user);
    }

    @PreAuthorize("@Security.isTeamMember(#name, null)")
    public List<RequestDTO> getActiveRequests(@P("name") String name) {
        List<RequestDTO> response = new ArrayList<>();
        UserEntity user = userRepository.findByLogin(name).get(0);
        if (user.getRole().equals(Role.ADMIN)) {
            for (RequestEntity entity : requestRepository.findAll()) {
                if ((entity.getStatus().equals(Status.CONSIDER))) {
                    response.add(toDTO(entity));
                }
            }
        } else if (user.getRole().equals(Role.DIRECTOR)) {
            List<TeamEntity> directorsTeams = teamRepository.findAllByDepartment(departmentRepository.findByDirector(user).get(0));
            for (RequestEntity entity : requestRepository.findAll()) {
                for (TeamEntity team : directorsTeams) {
                    if ((entity.getStatus().equals(Status.CONSIDER)) && (team.equals(entity.getUser().getTeam()))) {
                        response.add(toDTO(entity));
                        break;
                    }
                }
            }
        } else {
            List<TeamEntity> managersTeams = teamRepository.findAllByManager(user);
            for (RequestEntity entity : requestRepository.findAll()) {
                for (TeamEntity team : managersTeams) {
                    if ((entity.getStatus().equals(Status.CONSIDER)) && (team.equals(entity.getUser().getTeam()))) {
                        response.add(toDTO(entity));
                        break;
                    }
                }
            }
        }
        return response;
    }

    @PreAuthorize("@Security.isTeamMember(#name, null)")
    public List<RequestDTO> getResolvedRequests(@P("name") String name) {
        List<RequestDTO> response = new ArrayList<>();
        UserEntity user = userRepository.findByLogin(name).get(0);
        if (user.getRole().equals(Role.ADMIN)) {
            for (RequestEntity entity : requestRepository.findAll()) {
                if (!entity.getStatus().equals(Status.CONSIDER)) {
                    response.add(toDTO(entity));
                }
            }
        } else if (user.getRole().equals(Role.DIRECTOR)) {
            List<TeamEntity> directorsTeams = teamRepository.findAllByDepartment(departmentRepository.findByDirector(user).get(0));
            for (RequestEntity entity : requestRepository.findAll()) {
                for (TeamEntity team : directorsTeams) {
                    if ((!entity.getTypeOfRequest().getNeedApproval()
                            || !entity.getStatus().equals(Status.CONSIDER)) && (team.equals(entity.getUser().getTeam()))) {
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
                            || !entity.getStatus().equals(Status.CONSIDER)) && (team.equals(entity.getUser().getTeam()))) {
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
        if ((name == null || name.isEmpty()) && (familyName == null || familyName.isEmpty())) {
            requestDTO.setName("-");
        } else if (familyName == null || familyName.isEmpty()) {
            requestDTO.setName(entity.getUser().getName());
        } else if (name == null || name.isEmpty()) {
            requestDTO.setName(entity.getUser().getFamilyName());
        } else {
            requestDTO.setName(entity.getUser().getName() + " " + entity.getUser().getFamilyName());
        }
        if (entity.getUser().getTeam() != null) {
            requestDTO.setTeamName(entity.getUser().getTeam().getName());
        } else {
            requestDTO.setTeamName("-");
        }
        if (entity.getUser().getTeam() != null) {
            requestDTO.setTeamName(entity.getUser().getTeam().getName());
        } else {
            requestDTO.setTeamName("-");
        }
        requestDTO.setName(entity.getUser().getName() + " " + entity.getUser().getFamilyName());
        requestDTO.setDescription(entity.getDescription());
        requestDTO.setStart(entity.getBeginning());
        requestDTO.setEnd(entity.getEnding());
        requestDTO.setType(entity.getTypeOfRequest().getName());
        requestDTO.setStatus(entity.getStatus().getName());
        return requestDTO;
    }


    public boolean isManagerOnRest() {
        String login = SecurityExpressionMethods.currentUserLogin();
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

    private Date prepareDate(Date start) {
        long timeadj = 24 * 60 * 60 * 1000;
        return new Date(start.getTime() + timeadj);
    }

}
