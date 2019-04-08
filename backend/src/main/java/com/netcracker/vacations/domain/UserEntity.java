package com.netcracker.vacations.domain;

import com.netcracker.vacations.domain.enums.Role;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "increment")

    @Column(name = "users_id")
        private Integer usersId;

    @Column(name = "login", nullable = false, unique = true)
        private String login;

    @Column(name = "password", nullable = false)
        private String password;

    @Column(name = "role", nullable = false)
        private String role;

    @Column(name = "rest_days", nullable = false)
        private Integer restDays;

    @Column(name = "hire_date", nullable = false)
    @Temporal(TemporalType.DATE)
        private Date hireDate;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "teams_id")
        private TeamsEntity teamsId;
    @Column(name = "name")
        private String name;
    @Column(name = "surname")
        private String surname;
    @Column(name = "family_name")
        private String familyName;
    @Column(name = "phone_number")
        private String phoneNumber;
    @Column(name = "email")
        private String email;
    @Column(name = "description")
        private String description;
    @Transient
    private BCryptPasswordEncoder coder;

    public UserEntity() {
    }

    public UserEntity(String login, String password, Role role, Integer restDays, Date hireDate, TeamsEntity teamsId, String name, String surname, String familyName, String phoneNumber, String email, String description) {
        this.login = login;
        this.password = encode(password);
        this.role = role.name;
        this.restDays = restDays;
        this.hireDate = hireDate;
        this.teamsId = teamsId;
        this.name = name;
        this.surname = surname;
        this.familyName = familyName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.description = description;
    }

    public UserEntity(String login, String password, Role role, Integer restDays, Date hireDate, TeamsEntity teamsId, String name, String surname, String familyName, String phoneNumber, String email) {
        this.login = login;
        this.password = encode(password);
        this.role = role.name;
        this.restDays = restDays;
        this.hireDate = hireDate;
        this.teamsId = teamsId;
        this.name = name;
        this.surname = surname;
        this.familyName = familyName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public UserEntity(String login, String password, Role role, Integer restDays, Date hireDate, TeamsEntity teamsId, String name, String surname, String familyName, String phoneNumber) {
        this.login = login;
        this.password = encode(password);
        this.role = role.name;
        this.restDays = restDays;
        this.hireDate = hireDate;
        this.teamsId = teamsId;
        this.name = name;
        this.surname = surname;
        this.familyName = familyName;
        this.phoneNumber = phoneNumber;
    }

    public UserEntity(String login, String password, Role role, Integer restDays, Date hireDate, TeamsEntity teamsId, String name, String surname, String familyName) {
        this.login = login;
        this.password = encode(password);
        this.role = role.name;
        this.restDays = restDays;
        this.hireDate = hireDate;
        this.teamsId = teamsId;
        this.name = name;
        this.surname = surname;
        this.familyName = familyName;
    }

    public UserEntity(String login, String password, Role role, Integer restDays, Date hireDate, TeamsEntity teamsId, String name, String surname) {
        this.login = login;
        this.password = encode(password);
        this.role = role.name;
        this.restDays = restDays;
        this.hireDate = hireDate;
        this.teamsId = teamsId;
        this.name = name;
        this.surname = surname;
    }

    public UserEntity(String login, String password, Role role, Integer restDays, Date hireDate, TeamsEntity teamsId, String name) {
        this.login = login;
        this.password = encode(password);
        this.role = role.name;
        this.restDays = restDays;
        this.hireDate = hireDate;
        this.teamsId = teamsId;
        this.name = name;
    }

    public UserEntity(String login, String password, Role role, Integer restDays, Date hireDate, TeamsEntity teamsId) {
        this.login = login;
        this.password = encode(password);
        this.role = role.name;
        this.restDays = restDays;
        this.hireDate = hireDate;
        this.teamsId = teamsId;
    }

    public UserEntity(String login, String password, Role role, Integer restDays, Date hireDate) {
        this.login = login;
        this.password= encode(password);
        this.role = role.name;
        this.restDays = restDays;
        this.hireDate = hireDate;
    }

    public Integer getUsersId() {
        return usersId;
    }

    public void setUsersId(Integer usersId) {
        this.usersId = usersId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role.name;
    }

    public Integer getRestDays() {
        return restDays;
    }

    public void setRestDays(Integer restDays) {
        this.restDays = restDays;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public TeamsEntity getTeamsId() {
        return teamsId;
    }

    public void setTeamsId(TeamsEntity teamsId) {
        this.teamsId = teamsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String encode(String password){
        coder=new BCryptPasswordEncoder();
        password=coder.encode(password);
        return password;
    }
    public boolean checkPassword(String inputPassword){
        coder=new BCryptPasswordEncoder();
        Boolean result=coder.matches(inputPassword,this.password);
        return result;
    }
}
