package com.university.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "dept_name", nullable = false)
    private String deptName;

    @Column(name = "building")
    private String building;

    @Column(name = "budget")
    private Double budget;

    public Department() {}

    public Integer getDeptId() { return deptId; }
    public void setDeptId(Integer deptId) { this.deptId = deptId; }
    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }
    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }
}
