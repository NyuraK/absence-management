package com.netcracker.vacations.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "teams")
public class TeamEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")

    @Column(name = "teams_id")
    private Integer teamsId;

    @Column(name = "abscense_quota", nullable = false)
    private Integer quota;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})

    @JoinColumn(name = "managers_id")
    private UserEntity managersId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})

    @JoinColumn(name = "departments_id")
    private DepartmentEntity departmentsId;


    public TeamEntity() {
    }

    public TeamEntity(Integer quota, UserEntity managersId, String name) {
        this.quota = quota;
        this.managersId = managersId;
        this.name = name;
    }

    public Integer getTeamsId() {
        return teamsId;
    }

    public void setTeamsId(Integer teamsId) {
        this.teamsId = teamsId;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public UserEntity getManagersId() {
        return managersId;
    }

    public void setManagersId(UserEntity managersId) {
        this.managersId = managersId;
    }

    public DepartmentEntity getDepartmentsId() {
        return departmentsId;
    }

    public void setDepartmentsId(DepartmentEntity departmentsId) {
        this.departmentsId = departmentsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
