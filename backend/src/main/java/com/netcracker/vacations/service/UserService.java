package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.domain.enums.Role;
import com.netcracker.vacations.dto.UserDTO;
import com.netcracker.vacations.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserDTO> getUsers() {
        List<UserDTO> response = new ArrayList<>();
        for (UserEntity entity : userRepository.findAll()) {
            response.add(toDTO(entity));
        }
        return response;
    }

    public UserDTO getUser(Integer id) {
        return toDTO(userRepository.findById(id).get());
    }

    public List<UserDTO> getUsersFromTeam(Integer teamId){
        List<UserDTO> response = new ArrayList<>();
        for (UserEntity entity : userRepository.findAllByTeam_TeamsId(teamId)) {
            response.add(toDTO(entity));
        }
        return response;
    }

    public UserDTO addUser(UserDTO userDTO) {
        userRepository.save(toEntity(userDTO));
        return userDTO;
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        UserEntity userEntity = userRepository.findByUsersId(id).get(0);
        BeanUtils.copyProperties(toEntity(userDTO), userEntity, "usersId", "password");
        userRepository.save(userEntity);
        return userDTO;
    }

    public void updatePassword(Integer id, String password) {
        UserEntity userEntity = userRepository.findByUsersId(id).get(0);
        userEntity.setPassword(password);
    }

    private UserEntity toEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        TeamEntity teamEntity = new TeamEntity();
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
        return userEntity;
    }

    private UserDTO toDTO(UserEntity entity) {
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
        userDTO.setTeamId(entity.getTeam() == null ? null : entity.getTeam().getTeamsId());
        userDTO.setTeamName(entity.getTeam() == null ? null : entity.getTeam().getName());
        return userDTO;
    }


}
