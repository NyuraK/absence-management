package com.netcracker.vacations.converter;

import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.domain.enums.Role;
import com.netcracker.vacations.dto.UserDTO;

public class UserConverter {

    public static UserEntity toEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        com.netcracker.vacations.domain.TeamEntity teamEntity = new com.netcracker.vacations.domain.TeamEntity();
        if (userDTO.getTeamId() == null || userDTO.getTeamId() == -1) {
            teamEntity = null;
        } else {
            teamEntity.setTeamsId(userDTO.getTeamId());
        }
        userEntity.setTeam(teamEntity);
        userEntity.setDescription(userDTO.getDescription());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setFamilyName(userDTO.getFamilyName());
        userEntity.setHireDate(userDTO.getHireDate());
        userEntity.setLogin(userDTO.getLogin());
        userEntity.setName(userDTO.getName());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setPhoneNumber(userDTO.getPhoneNumber());
        userEntity.setRestDays(userDTO.getRestDays());
        userEntity.setRole(Role.findByName(userDTO.getRole()));
        userEntity.setSurname(userDTO.getSurname());
        userEntity.setUsersId(userDTO.getUserId());
        userEntity.setActivationCode(userDTO.getActivationCode());
        return userEntity;
    }

    public static UserDTO toDTO(UserEntity entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(entity.getUsersId());
        userDTO.setDescription(entity.getDescription());
        userDTO.setEmail(entity.getEmail());
        userDTO.setFamilyName(entity.getFamilyName());
        userDTO.setHireDate(entity.getHireDate());
        userDTO.setName(entity.getName());
        userDTO.setSurname(entity.getSurname());
        userDTO.setPassword(entity.getPassword());
        userDTO.setPhoneNumber(entity.getPhoneNumber());
        userDTO.setRole(entity.getRole().getName());
        userDTO.setLogin(entity.getLogin());
        userDTO.setRestDays(entity.getRestDays());
        userDTO.setIntegrated(entity.getIntegrated());
        userDTO.setTeamId(entity.getTeam() == null ? null : entity.getTeam().getTeamsId());
        userDTO.setTeamName(entity.getTeam() == null ? null : entity.getTeam().getName());
        userDTO.setDepartmentId(entity.getTeam() == null ? null :
                entity.getTeam().getDepartment() == null ? null :
                        entity.getTeam().getDepartment().getDepartmentsId());
        return userDTO;
    }
}
