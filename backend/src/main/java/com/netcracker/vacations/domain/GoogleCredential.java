package com.netcracker.vacations.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "google_credentials")
public class GoogleCredential {

    @Id
    @Column(name = "id")
    private String valueId;

    @Column(name = "value")
    private String value;

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
