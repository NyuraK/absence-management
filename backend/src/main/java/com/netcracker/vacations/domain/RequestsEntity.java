package com.netcracker.vacations.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="requests_ent")
public class RequestsEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")

    @Column(name = "requests_id")
        private Integer requestsId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "users_id", nullable = false)
        private UsersEntity usersId;
    @Column(name = "beginning_ov_vacation", nullable=false)
    @Temporal(TemporalType.DATE)
        private Date beginning;
    @Column(name = "enging_ov_vacation", nullable=false)
    @Temporal(TemporalType.DATE)
        private Date ending;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "type_of_request_id", referencedColumnName = "type_of_requests_id")
        private TypeOfRequestsEntity typeOfRequest;
    @Column(name = "status", nullable=false)
        private String status;
    @Column(name = "description")
        private String description;

    public RequestsEntity() {
    }

    public RequestsEntity(UsersEntity usersId, Date beginning, Date ending, TypeOfRequestsEntity typeOfRequest, String status, String description) {
        this.usersId = usersId;
        this.beginning = beginning;
        this.ending = ending;
        this.typeOfRequest = typeOfRequest;
        this.status = status;
        this.description = description;
    }

    public RequestsEntity(UsersEntity usersId, Date beginning, Date ending, TypeOfRequestsEntity typeOfRequest, String status) {
        this.usersId = usersId;
        this.beginning = beginning;
        this.ending = ending;
        this.typeOfRequest = typeOfRequest;
        this.status = status;
    }

    public Integer getRequestsId() {
        return requestsId;
    }

    public void setRequestsId(Integer requestsId) {
        this.requestsId = requestsId;
    }

    public UsersEntity getUsersId() {
        return usersId;
    }

    public void setUsersId(UsersEntity usersId) {
        this.usersId = usersId;
    }

    public Date getBeginning() {
        return beginning;
    }

    public void setBeginning(Date beginning) {
        this.beginning = beginning;
    }

    public Date getEnding() {
        return ending;
    }

    public void setEnding(Date ending) {
        this.ending = ending;
    }

    public TypeOfRequestsEntity getTypeOfRequest() {
        return typeOfRequest;
    }

    public void setTypeOfRequest(TypeOfRequestsEntity typeOfRequest) {
        this.typeOfRequest = typeOfRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
