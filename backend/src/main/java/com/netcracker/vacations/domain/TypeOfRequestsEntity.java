package com.netcracker.vacations.domain;


import com.netcracker.vacations.domain.enums.RequestsTypes;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="type_of_requests__ent")
public class TypeOfRequestsEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")

    @Column(name = "type_of_requests_id")
        private Integer typeOfRequest;

    @Column(name = "is_influencing_on_vr", nullable=false)
        private Boolean influenceOnVr;

    @Column(name = "does_need_approval", nullable=false)
        private Boolean needApproval;

    @Column(name = "name", nullable=false, unique=true)
        private String name;

    public TypeOfRequestsEntity() {
    }

    public TypeOfRequestsEntity(Boolean influenceOnVr, Boolean needApproval, RequestsTypes name) {
        this.influenceOnVr = influenceOnVr;
        this.needApproval = needApproval;
        this.name = name.name;
    }

    public Integer getTypeOfRequest() {
        return typeOfRequest;
    }

    public void setTypeOfRequest(Integer typeOfRequest) {
        this.typeOfRequest = typeOfRequest;
    }

    public Boolean getInfluenceOnVr() {
        return influenceOnVr;
    }

    public void setInfluenceOnVr(Boolean influenceOnVr) {
        this.influenceOnVr = influenceOnVr;
    }

    public Boolean getNeedApproval() {
        return needApproval;
    }

    public void setNeedApproval(Boolean needApproval) {
        this.needApproval = needApproval;
    }

    public String getName() {
        return name;
    }

    public void setName(RequestsTypes name) {
        this.name = name.name;
    }
}
