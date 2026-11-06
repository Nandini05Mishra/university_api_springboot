package com.university.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "teaches")
public class Teaches {

    @EmbeddedId
    private TeachesId id;

    public Teaches() {}

    public TeachesId getId() { return id; }
    public void setId(TeachesId id) { this.id = id; }
}
