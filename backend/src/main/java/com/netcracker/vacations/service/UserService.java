package com.netcracker.vacations.service;

import com.netcracker.vacations.Util;
import com.netcracker.vacations.domain.TeamEntity;
import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.domain.enums.Role;
import com.netcracker.vacations.dto.TeamDTO;
import com.netcracker.vacations.dto.UserDTO;
import com.netcracker.vacations.repository.TeamRepository;
import com.netcracker.vacations.repository.UserRepository;
import com.netcracker.vacations.security.MyUserPrincipal;
import org.hibernate.engine.spi.Status;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    private UserRepository userRepository;

    private TeamRepository teamRepository;

    @Autowired
    public UserService(UserRepository userRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }


    public List<UserDTO> getUsers() {
        List<UserDTO> response = new ArrayList<>();
        for (UserEntity entity : userRepository.findAll()) {
            response.add(toDTO(entity));
        }
        return response;
    }

    public List<UserDTO> getUsersSubordinateToManager(Integer id) {
        List<UserDTO> response = new ArrayList<>();
        for (UserEntity entity : userRepository.findAllByTeamManagerUsersId(id)) {
            response.add(toDTO(entity));
        }
        return response;
    }

    public UserDTO getUser(Integer id) {
        return toDTO(userRepository.findById(id).get());
    }

    public List<UserDTO> getUsersFromTeam(Integer teamId) {
        List<UserDTO> response = new ArrayList<>();
        for (UserEntity entity : userRepository.findAllByTeamTeamsId(teamId)) {
            response.add(toDTO(entity));
        }
        return response;
    }

    public String getUsersName(HttpServletRequest request) {
        String name = Util.extractLoginFromRequest(request);
        UserEntity currentUser = userRepository.findByLogin(name).get(0);
        String output = (currentUser.getRole().getName() + ": " + currentUser.getName() + " " + currentUser.getSurname());
        return output;
    }

    public UserDTO getUserInfo(HttpServletRequest request) {
        String login = Util.extractLoginFromRequest(request);
        UserEntity currentUser = userRepository.findByLogin(login).get(0);
        UserDTO user = toDTO(currentUser);
        if (currentUser.getRole().equals(Role.MANAGER)) {
            List<String> subordinateTeamsLines = new ArrayList<>();
            List<TeamEntity> subordinateTeams = teamRepository.findAllByManager(currentUser);
            if (subordinateTeams.isEmpty()) {
                user.setSubordinateTeams("-");
            } else {
                for (TeamEntity team : subordinateTeams) {
                    subordinateTeamsLines.add(team.getName());
                }
                String teams = String.join(", ", subordinateTeamsLines);
                user.setSubordinateTeams(teams);
            }
        }
        if (currentUser.getFamilyName()==null||currentUser.getFamilyName().isEmpty()) {
            user.setFamilyName("-");
        }
        if (currentUser.getTeam() == null) {
            user.setTeamName("-");
        }
        if (currentUser.getPhoneNumber()==null||currentUser.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber("-");
        }
        if (currentUser.getDescription()==null||currentUser.getDescription().isEmpty()) {
            user.setDescription("-");
        }
        return user;
    }

    public UserDTO addUser(UserDTO userDTO) {
        userDTO.setPassword(UUID.randomUUID().toString());
        sendMailPassword(userDTO);
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

    public void updatePassword(Integer id, String password, String mode) {
        UserEntity userEntity;
        if (mode.equals("UserChange")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String login = ((MyUserPrincipal) authentication.getPrincipal()).getUser().getLogin();
            List<UserEntity> users = userRepository.findByLogin(login);
            if (!users.isEmpty()) {
                userEntity = users.get(0);
                userEntity.setPassword(password);
            }
        } else {
            userEntity = userRepository.findByUsersId(id).get(0);
            userEntity.setPassword(password);
        }
    }

    public boolean checkPassword(String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = ((MyUserPrincipal) authentication.getPrincipal()).getUser().getLogin();
        UserEntity currentUser = userRepository.findByLogin(login).get(0);
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        return bcrypt.matches(password, currentUser.getPassword());
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
        userEntity.setActivationCode(userDTO.getActivationCode());
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
        userDTO.setDepartmentId(entity.getTeam() == null ? null :
                entity.getTeam().getDepartment() == null ? null :
                        entity.getTeam().getDepartment().getDepartmentsId());
        return userDTO;
    }

    public String getUserByCode(String code) {
        if (userRepository.findByActivationCode(code).get(0) != null) {
            return userRepository.findByActivationCode(code).get(0).getLogin();
        }
        return null;
    }

    public void changePassword(List<String> userInfo) {
        String password = userInfo.get(0);
        String login = userInfo.get(1);
        if (!password.isEmpty() && !login.isEmpty()) {
            UserEntity user = userRepository.findByLogin(login).get(0);
            user.setPassword(password);
            user.setActivationCode(null);
            userRepository.save(user);
        }
    }

    public boolean sendMailForgot(String email) {
        boolean isSent = false;
        if (email != null) {
            List<UserEntity> users = userRepository.findByEmail(email);
            if(!users.isEmpty()) {
                UserEntity user=users.get(0);
                user.setActivationCode(UUID.randomUUID().toString());
                String message = String.format("Dear " + user.getName() + " " + user.getSurname() + ",\n" + "if you can not use your old password, you can pick a new one. " +
                        "For doing this visit next link: http://localhost:8080/activation/"+user.getActivationCode());
                send(email, "Changing your password.", message);
                isSent=true;
            }
        }
        return isSent;
    }

    public UserDTO sendMailPassword(UserDTO user) {
        if (user.getEmail() != null) {
            String message = String.format("Dear " + user.getName() + " " + user.getSurname() + ",\n" + "you successfully registered your account. " +
                    "Now your username is \"" + user.getLogin() + "\" and your password is \"" + user.getPassword() + "\". You can change your password on your account. For authorisation visit next link: http://localhost:8080");
            send(user.getEmail(), "Account activation, password changing.", message);
        }
        return user;
    }

    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    public TeamDTO getUserTeam(String username) {
        TeamEntity team = userRepository.findByLogin(username).get(0).getTeam();
        return new TeamDTO().setTeamId(team.getTeamsId()).setName(team.getName());
    }

    public Integer getRestDays(String username) {
        return userRepository.findByLogin(username).get(0).getRestDays();
    }
}
