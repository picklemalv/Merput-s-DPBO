/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;
import Exception.*;

/**
 *
 * @author piannn
 */
public interface TaskAction {
    
    void createTask(String taskName, String teamName);
    void deleteTask(int taskId) throws TaskNotFoundException;
    void createSubTask(int taskId, String subTaskName, String dueDate) throws TaskNotFoundException;
    void assignSubTask(int subTaskId, String username) throws TaskNotFoundException;
    void updateSubTaskStatus(int subTaskId, String status) throws TaskNotFoundException;
    void viewTask();
    void viewMyTask(String username);
    void listMySubTask(String username);
}
