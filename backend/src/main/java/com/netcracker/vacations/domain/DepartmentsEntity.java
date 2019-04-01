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
    @Column(name = "directors_id")
        private Integer directorsId;

    public DepartmentsEntity() {
    }

    public DepartmentsEntity(Integer directorsId) {
        this.directorsId=directorsId;
    }

    public Integer getDepartmentsId() {
        return departmentsId;
    }

    public void setDepartmentsId(Integer departmentsId) {
        this.departmentsId = departmentsId;
    }

    public Integer getDirectorsId() {
        return directorsId;
    }

    public void setDirectorsId(Integer directorsId) {
        this.directorsId = directorsId;
    }
}

