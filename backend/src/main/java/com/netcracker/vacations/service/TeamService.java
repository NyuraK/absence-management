package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.DepartmentEntity;
import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.dto.TeamDTO;
import com.netcracker.vacations.repository.TeamRepository;
import com.netcracker.vacations.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TeamService {

    private TeamRepository teamRepository;
    private UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {

        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
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

    public List<String> getMembers() {
        List<String> res = new ArrayList<>();
        for (UserEntity user : userRepository.findAll()) {
            res.add(user.getName() + " " + user.getFamilyName());
        }
        return res;
    }
}
