package com.example.hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "department")
public class Department {

    //Getters and Setters (Handled by Lombok)
    @Id
    @Column(name = "DepartmentID")
    private int departmentId;

    @Column(name = "Name", nullable = false, length = 30)
    private String name;

    @Column(name = "Head", nullable = false)
    private int head;

    //Commented relationships for future use (matching your Physician format)

    //One Department → Has One Head Physician
    @OneToOne
    @JoinColumn(name = "Head", insertable = false, updatable = false)
    private Physician headPhysician;

//    //Many-to-Many with Physician (via affiliated_with)
    @ManyToMany(mappedBy = "departments")
    private List<Physician> affiliatedPhysicians;

    //Constructors
    public Department() {
    }

    public Department(int departmentId, String name, int head) {
        this.departmentId = departmentId;
        this.name = name;
        this.head = head;
    }
}
