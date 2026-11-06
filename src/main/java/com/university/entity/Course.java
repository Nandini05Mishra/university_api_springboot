package com.university.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "course_title", nullable = false)
    private String courseTitle;

    @Column(name = "credits")
    private Integer credits;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    public Course() {}

    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
}
