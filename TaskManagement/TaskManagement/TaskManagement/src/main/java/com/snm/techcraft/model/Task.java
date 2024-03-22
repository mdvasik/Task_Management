package com.snm.techcraft.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private Integer taskId;

    private String name;   //filter

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "work_category_id")
    private WorkCategory workCategory; 

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;      

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client; 

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id") 
    private UserInfo assignName;  

    @ManyToOne
    @JoinColumn(name = "cowork_user_information_id") 
    private UserInfo coWork;

    private String status; // filter

    private String notes;

    private LocalDate assignDate;

    private LocalDate taskEndDate;

    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "user_id_createdBy") 
    private UserInfo createdBy;

    private LocalDate createOn;

    private String updateBy;   

    private LocalDate updateOn;
    
    @Column(name = "deleted")
    private boolean deleted;
}
