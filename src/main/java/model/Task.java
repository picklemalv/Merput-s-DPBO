/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author piannn
 */
public class Task {
    private int id;
    private String taskName;
    private String teamName;
    private ArrayList<SubTask> subTasks;

    public Task(int id, String taskName, String teamName) {

        this.id = id;
        this.taskName = taskName;
        this.teamName = teamName;
        this.subTasks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTeamName() {
        return teamName;
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }
}
