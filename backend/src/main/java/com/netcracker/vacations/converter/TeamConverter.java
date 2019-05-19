package com.netcracker.vacations.converter;

import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.dto.TeamDTO;

public class TeamConverter {

    public static TeamDTO convert(TeamEntity teamEntity) {
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

}
