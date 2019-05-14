package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.RequestEntity;
import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.dto.AbsenceDTO;
import com.netcracker.vacations.dto.TeamDTO;
import com.netcracker.vacations.repository.DepartmentRepository;
import com.netcracker.vacations.repository.RequestRepository;
import com.netcracker.vacations.repository.TeamRepository;
import com.netcracker.vacations.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class TeamService {

    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;
    private RequestRepository requestRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository, DepartmentRepository departmentRepository, RequestRepository requestRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
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
        if (teamRepository.findById(id).isPresent()) {
            return toDTO(teamRepository.findById(id).get());
        };
        return null;
    }

    public List<TeamDTO> getTeamsFromDepartment(Integer id) {
        List<TeamDTO> response = new ArrayList<>();
        for (TeamEntity entity : teamRepository.findAllByDepartmentDepartmentsId(id)) {
            response.add(toDTO(entity));
        }
        return response;
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
        teamEntity.setTeamsId(null);
        teamEntity.setName(teamDTO.getName());
        teamEntity.setQuota(teamDTO.getQuota());

        if (teamDTO.getDepartmentId() == null) {
            teamEntity.setDepartment(null);
        } else {
            teamEntity.setDepartment(departmentRepository.findByDepartmentsId(teamDTO.getDepartmentId()).get(0));
        }

        if (teamDTO.getManagerId() == null) {
            teamEntity.setManager(null);
        } else {
            teamEntity.setManager(userRepository.findByUsersId(teamDTO.getManagerId()).get(0));
        }
        return teamEntity;
    }

    private TeamDTO toDTO(TeamEntity teamEntity) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamId(teamEntity.getTeamsId());
        teamDTO.setName(teamEntity.getName());
        teamDTO.setQuota(teamEntity.getQuota());
        teamDTO.setDepartmentId(teamEntity.getDepartment() == null ? null : teamEntity.getDepartment().getDepartmentsId());
        teamDTO.setDepartmentName(teamEntity.getDepartment() == null ? null : teamEntity.getDepartment().getName());
        teamDTO.setManagerId(teamEntity.getManager() == null ? null : teamEntity.getManager().getUsersId());
        teamDTO.setManagerName(teamEntity.getManager() == null ? null : teamEntity.getManager().getName());
        teamDTO.setManagerSurname(teamEntity.getManager() == null ? null : teamEntity.getManager().getSurname());
        return teamDTO;
    }

    @PreAuthorize("@Security.isTeamMember(#username, #teamId)")
    public List<AbsenceDTO> getTeamMembers(@P("username") String username, @P("teamId") Integer teamId) {
        List<AbsenceDTO> res = new ArrayList<>();
        for (UserEntity user : userRepository.findAllByTeamTeamsId(teamId)) {
            res.add(toAbsenceDTO(user, new RequestEntity()));
        }
        return res;
    }

    @PreAuthorize("@Security.isTeamMember(#username, null)")
    public List<AbsenceDTO> getTeamMembers(@P("username") String username) {
        List<AbsenceDTO> res = new ArrayList<>();
        Integer teamsId = userRepository.findByLogin(username).get(0).getTeam().getTeamsId();
        for (UserEntity user : userRepository.findAllByTeamTeamsId(teamsId)) {
            AbsenceDTO absenceDTO = toAbsenceDTO(user, new RequestEntity());
            absenceDTO.setTeamID(teamsId);
            res.add(absenceDTO);
        }
        return res;
    }

    @PreAuthorize("@Security.isAllowed(#username)")
    public List<TeamDTO> getManagerTeams(@P("username") String username) {
        UserEntity manager = userRepository.findByLogin(username).get(0);
        List<TeamDTO> teams = new ArrayList<>();
        for (TeamEntity team : teamRepository.findAllByManager(manager))
            teams.add(toDTO(team));
        teams.add(toDTO(manager.getTeam()));
        return teams;
    }

    @PreAuthorize("@Security.isTeamMember(#username, #teamID)")
    public List<AbsenceDTO> getTeamAbsences(@P("username") String username, Integer teamID) {
        List<UserEntity> team = userRepository.findAllByTeamTeamsId(teamID);
        List<RequestEntity> requests = requestRepository.findAllByStatus(Status.ACCEPTED);

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
        absenceDTO.setSurname(user.getSurname());
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
