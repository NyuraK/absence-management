package com.netcracker.vacations.domain;

import javax.persistence.*;

@Entity
@Table(name="departments")
public class DepartmentEntity {
    @Id
    @GeneratedValue(generator = "increment")

    @Column(name = "departments_id")
        private Integer departmentsId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "directors_id")
        private UserEntity directorsId;

    public DepartmentEntity() {
    }

    public DepartmentEntity(UserEntity directorsId) {
        this.directorsId=directorsId;
    }

    public Integer getDepartmentsId() {
        return departmentsId;
    }

    public void setDepartmentsId(Integer departmentsId) {
        this.departmentsId = departmentsId;
    }

    public UserEntity getDirectorsId() {
        return directorsId;
    }

    public void setDirectorsId(UserEntity directorsId) {
        this.directorsId = directorsId;
    }
}

