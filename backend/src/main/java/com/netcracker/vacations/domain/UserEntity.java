package com.netcracker.vacations.domain;

import com.netcracker.vacations.domain.enums.Role;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users", indexes = {
        @Index(columnList = "users_id", name = "idIndex"),
        @Index(columnList = "login", name = "loginIndex"),
        @Index(columnList = "teams_id", name = "teamIndex"),
        @Index(columnList = "email", name = "emailIndex"),
        @Index(columnList = "activation_code", name = "codeIndex"),
})
public class UserEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "users_id")
    private Integer usersId;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "rest_days", nullable = false, precision=8, scale=2)
    private Double restDays = 0.0;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teams_id")
    private TeamEntity team;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "integrated", nullable = false)
    private Boolean integrated = false;

    @Column(name = "last_visit")
    private LocalDate lastVisit;


    @Transient
    private BCryptPasswordEncoder coder;

    public UserEntity() {
    }

    public UserEntity(String login, String password, Role role, Double restDays, LocalDate hireDate, String name, String surname, String email) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.restDays = restDays;
        this.hireDate = hireDate;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    @PrePersist
    public void prePersist() {
        if (integrated == null) //We set default value in case if the value is not set yet.
            integrated = false;
        if (restDays == null) {
            restDays = 0.0;
        }
    }

    public LocalDate getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(LocalDate lastVisit) {
        this.lastVisit = lastVisit;
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
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Double getRestDays() {
        return restDays;
    }

    public void setRestDays(Double restDays) {
        this.restDays = restDays;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
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

    public Boolean getIntegrated() {
        return integrated;
    }

    public void setIntegrated(Boolean integrated) {
        this.integrated = integrated;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String encode(String password) {
        coder = new BCryptPasswordEncoder();
        password = coder.encode(password);
        return password;
    }

    public boolean checkPassword(String inputPassword) {
        coder = new BCryptPasswordEncoder();
        Boolean result = coder.matches(inputPassword, this.password);
        return result;
    }
}
