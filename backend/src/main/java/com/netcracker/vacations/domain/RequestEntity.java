package com.netcracker.vacations.domain;

import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.exception.BeginningAfterEndingException;
import com.netcracker.vacations.exception.EndingBeforeBeginningException;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "requests")
public class RequestEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "request_id")
    private Integer requestsId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "begin_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date beginning;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date ending;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "type_of_request_id", referencedColumnName = "type_of_requests_id")
    private RequestTypeEntity typeOfRequest;

    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "description")
    private String description;

    public RequestEntity() {
    }

    public RequestEntity(UserEntity user, Date beginning, Date ending, RequestTypeEntity typeOfRequest, Status status) throws EndingBeforeBeginningException {
        this.user = user;
        this.typeOfRequest = typeOfRequest;
        this.status = status;

        if (ending.before(beginning)) {
            throw new EndingBeforeBeginningException();
        } else {
            this.beginning = beginning;
            this.ending = ending;
        }
    }

    public Integer getRequestsId() {
        return requestsId;
    }

    public void setRequestsId(Integer requestsId) {
        this.requestsId = requestsId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Date getBeginning() {
        return beginning;
    }

    public void setBeginning(Date beginning) throws BeginningAfterEndingException {
        if ((beginning.after(ending)) && (ending != null)) {
            throw new BeginningAfterEndingException();
        } else {
            this.beginning = beginning;
        }
    }

    public Date getEnding() {
        return ending;
    }

    public void setEnding(Date ending) throws EndingBeforeBeginningException {
        if ((ending.before(beginning)) && (beginning != null)) {
            throw new EndingBeforeBeginningException();
        } else {
            this.ending = ending;
        }
    }

    public RequestTypeEntity getTypeOfRequest() {
        return typeOfRequest;
    }

    public void setTypeOfRequest(RequestTypeEntity typeOfRequest) {
        this.typeOfRequest = typeOfRequest;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
