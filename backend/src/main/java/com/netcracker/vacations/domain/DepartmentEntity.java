package com.netcracker.vacations.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "departments")
public class DepartmentEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "departments_id")
    private Integer departmentsId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "directors_id")
    private UserEntity director;

    public DepartmentEntity() {
    }

    public DepartmentEntity(UserEntity directorsId) {
        this.director = directorsId;
    }

    public Integer getDepartmentsId() {
        return departmentsId;
    }

    public void setDepartmentsId(Integer departmentsId) {
        this.departmentsId = departmentsId;
    }

    public UserEntity getDirector() {
        return director;
    }

    public void setDirector(UserEntity director) {
        this.director = director;
    }
}

