package com.netcracker.vacations.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "google_credentials")
public class GoogleCredential {

    @Id
    @Column(name = "id")
    private String valueId;

    @Column(name = "value")
    @Type(type = "text")
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
