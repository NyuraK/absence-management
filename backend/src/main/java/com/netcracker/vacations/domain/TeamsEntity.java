package com.netcracker.vacations.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="teams_ent")
public class TeamsEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")


    @Column(name = "teams_id")
        private Integer teamsId;

    @Column(name = "employees_on_red", nullable=false)
        private Integer red;

    @Column(name = "employees_on_green", nullable=false)
        private Integer green;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})

    @JoinColumn(name = "managers_id")
        private UsersEntity managersId;

    @Column(name = "name", nullable = false, unique = true)
        private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})

    @JoinColumn(name = "departments_id")
        private DepartmentsEntity departmentsId;


    public TeamsEntity() {
    }

    public TeamsEntity(Integer red, Integer green, UsersEntity managersId, DepartmentsEntity departmentsId, String name) {
        this.red = 5;
        this.green = 2;
        this.managersId = managersId;
        this.departmentsId = departmentsId;
        this.name = name;
    }

    public TeamsEntity(Integer red, Integer green, UsersEntity managersId, String name) {
        this.red = 5;
        this.green = 2;
        this.managersId = managersId;
        this.name = name;
    }

    public Integer getTeamsId() {
        return teamsId;
    }

    public void setTeamsId(Integer teamsId) {
        this.teamsId = teamsId;
    }

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public UsersEntity getManagersId() {
        return managersId;
    }

    public void setManagersId(UsersEntity managersId) {
        this.managersId = managersId;
    }

    public DepartmentsEntity getDepartmentsId() {
        return departmentsId;
    }

    public void setDepartmentsId(DepartmentsEntity departmentsId) {
        this.departmentsId = departmentsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
