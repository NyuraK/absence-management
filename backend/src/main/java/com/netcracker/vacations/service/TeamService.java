package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.DepartmentEntity;
import com.netcracker.vacations.domain.RequestEntity;
import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.dto.AbsenceDTO;
import com.netcracker.vacations.dto.TeamDTO;
import com.netcracker.vacations.dto.UserDTO;
import com.netcracker.vacations.repository.RequestRepository;
import com.netcracker.vacations.repository.TeamRepository;
import com.netcracker.vacations.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class TeamService {

    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private RequestRepository requestRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository, RequestRepository requestRepository) {

        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    public List<TeamDTO> getTeams() {
        List<TeamDTO> response = new ArrayList<>();
        for (TeamEntity entity : teamRepository.findAll()) {
            response.add(toDTO(entity));
        }
        return response;
    }

    public TeamDTO getTeam(Integer id) {
        return toDTO(teamRepository.findById(id).get());
    }

    public TeamDTO addTeam(TeamDTO teamDTO) {
        teamRepository.save(toEntity(teamDTO));
        return teamDTO;
    }

    public void deleteTeam(Integer id) {
        teamRepository.deleteById(id);
    }

    public TeamDTO updateTeam(Integer id, TeamDTO teamDTO) {
        TeamEntity teamEntity = teamRepository.findByTeamsId(id).get(0);
        BeanUtils.copyProperties(toEntity(teamDTO), teamEntity, "teamsId");
        teamRepository.save(teamEntity);
        return teamDTO;
    }

    private TeamEntity toEntity(TeamDTO teamDTO) {
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamsId(teamDTO.getTeamId());
        teamEntity.setName(teamDTO.getName());
        teamEntity.setQuota(teamDTO.getQuota());

        DepartmentEntity departmentEntity = new DepartmentEntity();
        if (teamDTO.getDepartmentId() == null) {
            departmentEntity = null;
        } else {
            departmentEntity.setDepartmentsId(teamDTO.getDepartmentId());
        }
        teamEntity.setDepartment(departmentEntity);

        UserEntity manager = new UserEntity();
        if (teamDTO.getManagerId() == null) {
            manager = null;
        } else {
            manager.setUsersId(teamDTO.getManagerId());
        }
        teamEntity.setManager(manager);
        return teamEntity;
    }

    private TeamDTO toDTO(TeamEntity teamEntity) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamId(teamEntity.getTeamsId());
        teamDTO.setName(teamEntity.getName());
        teamDTO.setQuota(teamEntity.getQuota());
        teamDTO.setDepartmentId(teamEntity.getDepartment() == null ? null : teamEntity.getDepartment().getDepartmentsId());
        teamDTO.setManagerId(teamEntity.getManager() == null ? null : teamEntity.getManager().getUsersId());
        teamDTO.setManagerName(teamEntity.getManager() == null ? null : teamEntity.getManager().getName());
        teamDTO.setManagerSurname(teamEntity.getManager() == null ? null : teamEntity.getManager().getSurname());
        return teamDTO;
    }

    public List<AbsenceDTO> getTeamMembers(Integer id) {
        List<AbsenceDTO> res = new ArrayList<>();
        for (UserEntity user : userRepository.findAllByTeam_TeamsId(id)) {
            res.add(toAbsenceDTO(user, new RequestEntity()));
        }
        return res;
    }

    public List<AbsenceDTO> getTeamMembers(String username) {
        List<AbsenceDTO> res = new ArrayList<>();
        Integer teamsId = userRepository.findByLogin(username).get(0).getTeam().getTeamsId();
        for (UserEntity user : userRepository.findAllByTeam_TeamsId(teamsId)) {
            AbsenceDTO absenceDTO = toAbsenceDTO(user, new RequestEntity());
            absenceDTO.setTeamID(teamsId);
            res.add(absenceDTO);
        }
        return res;
    }

    public List<TeamDTO> getManagerTeams(String username) {
        UserEntity manager = userRepository.findByLogin(username).get(0);
        List<TeamDTO> teams = new ArrayList<>();
        for (TeamEntity team : teamRepository.findAllByManager(manager))
            teams.add(toDTO(team));
        return teams;
    }

    public List<AbsenceDTO> getTeamAbsences(String username, Integer teamID) {
        List<UserEntity> team = userRepository.findAllByTeam_TeamsId(teamID);
//        List<RequestEntity> requests = requestRepository.findAllByStatus(Status.CONSIDER.name);
        List<RequestEntity> requests = new ArrayList<>();

        for (RequestEntity entity : requestRepository.findAll())
            if (entity.getStatus().equals(Status.ACCEPTED.getName()))
                requests.add(entity);

        Map<UserEntity, List<RequestEntity>> absences = new HashMap<>();
        for (RequestEntity requestEntity : requests) {
            absences.computeIfAbsent(requestEntity.getUser(), k -> new ArrayList<>()).add(requestEntity);
        }

        List<AbsenceDTO> result = new ArrayList<>();

        for (UserEntity user : team) {
            if (absences.get(user) != null) {
                List<RequestEntity> temp = absences.get(user);
                for (RequestEntity request : temp) {
                    result.add(toAbsenceDTO(user, request));
                }
            }
        }
        return result;
    }

    private AbsenceDTO toAbsenceDTO(UserEntity user, RequestEntity requestEntity) {
        AbsenceDTO absenceDTO = new AbsenceDTO();
        absenceDTO.setName(user.getName());
        absenceDTO.setFamilyName(user.getFamilyName());
        if (requestEntity.getUser() != null) {
            absenceDTO.setType(requestEntity.getTypeOfRequest().getName());
            absenceDTO.setBegin(requestEntity.getBeginning().toString());
            absenceDTO.setEnd(requestEntity.getEnding().toString());
        } else {
            absenceDTO.setType("");
            absenceDTO.setBegin(new Date().toString());
            absenceDTO.setEnd(new Date().toString());
        }

        return absenceDTO;
    }
}