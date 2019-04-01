package com.netcracker.vacations.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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
    @Column(name = "name")
        private String name;

    public TypeOfRequestsEntity() {
    }

    public TypeOfRequestsEntity(Boolean influenceOnVr, Boolean needApproval, String name) {
        this.influenceOnVr = influenceOnVr;
        this.needApproval = needApproval;
        this.name = name;
    }

    public TypeOfRequestsEntity(Boolean influenceOnVr, Boolean needApproval) {
        this.influenceOnVr = influenceOnVr;
        this.needApproval = needApproval;
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

    public void setName(String name) {
        this.name = name;
    }
}
