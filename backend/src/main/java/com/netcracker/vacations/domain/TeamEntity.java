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

    @Column(name = "absence_quota", nullable = false)
    private Integer quota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managers_id")
    private UserEntity manager;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departments_id")
    private DepartmentEntity department;


    public TeamEntity() {
    }

    public TeamEntity(Integer quota, UserEntity managersId, String name) {
        this.quota = quota;
        this.manager = managersId;
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

    public UserEntity getManager() {
        return manager;
    }

    public void setManager(UserEntity manager) {
        this.manager = manager;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
