package com.university.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "enrollment")
public class Enrollment {

    @EmbeddedId
    private EnrollmentId id;

    @Column(name = "marks")
    private Integer marks;

    @Column(name = "attendance")
    private Integer attendance;   // percentage

    public Enrollment() {}

    public EnrollmentId getId() { return id; }
    public void setId(EnrollmentId id) { this.id = id; }
    public Integer getMarks() { return marks; }
    public void setMarks(Integer marks) { this.marks = marks; }
    public Integer getAttendance() { return attendance; }
    public void setAttendance(Integer attendance) { this.attendance = attendance; }
}
