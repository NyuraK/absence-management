package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.DepartmentEntity;
import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.dto.TeamDTO;
import com.netcracker.vacations.repository.TeamRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TeamService {

    private TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
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
        if (teamDTO.getDepartmentId() == null || teamDTO.getDepartmentId() == -1) {
            departmentEntity = null;
        } else {
            departmentEntity.setDepartmentsId(teamDTO.getDepartmentId());
        }
        teamEntity.setDepartment(departmentEntity);

        UserEntity manager = new UserEntity();
        if (teamDTO.getManagerId() == null || teamDTO.getManagerId() == -1) {
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
        teamDTO.setDepartmentId(teamEntity.getDepartment() == null ? -1 : teamEntity.getDepartment().getDepartmentsId());
        teamDTO.setManagerId(teamEntity.getManager() == null ? -1 : teamEntity.getManager().getUsersId());
        return teamDTO;
    }
}
