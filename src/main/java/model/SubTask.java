/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author piannn
 */
public class SubTask {

    private int id;
    private String subTaskName;
    private String assignedTo;
    private String status;
    private LocalDate dueDate;;

    public SubTask(int id, String subTaskName, String assignedTo, LocalDate dueDate) {

        this.id = id;
        this.subTaskName = subTaskName;
        this.assignedTo = assignedTo;
        this.dueDate = dueDate;


        if (assignedTo == null) {

            this.status = "UNASSIGNED";

        } else {

            this.status = "PENDING";
        }
    }

    public int getId() {

        return id;
    }

    public String getSubTaskName() {

        return subTaskName;
    }

    public String getAssignedTo() {

        return assignedTo;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public void setAssignedTo(String username) {

        this.assignedTo = username;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }    
}