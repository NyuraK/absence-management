package com.netcracker.vacations.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="departments_ent")
public class DepartmentsEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")

    @Column(name = "departments_id")
        private Integer departmentsId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "directors_id")
        private UsersEntity directorsId;

    public DepartmentsEntity() {
    }

    public DepartmentsEntity(UsersEntity directorsId) {
        this.directorsId=directorsId;
    }

    public Integer getDepartmentsId() {
        return departmentsId;
    }

    public void setDepartmentsId(Integer departmentsId) {
        this.departmentsId = departmentsId;
    }

    public UsersEntity getDirectorsId() {
        return directorsId;
    }

    public void setDirectorsId(UsersEntity directorsId) {
        this.directorsId = directorsId;
    }
}

