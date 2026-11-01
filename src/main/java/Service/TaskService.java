/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.*;
import interfaces.TaskAction;
import Exception.*;

/**
 *
 * @author piannn
 */
public class TaskService implements TaskAction{

    private static TaskService instance;

    private ArrayList<Task> tasks;
    private int nextTaskId = 1;
    private int nextSubTaskId = 1;

    private TaskService() {
        tasks = new ArrayList<>();
    }

    public static TaskService getInstance() {

        if (instance == null) {
            instance = new TaskService();
        }

        return instance;
    }

    // ================= CREATE TASK =================
    @Override
    public void createTask(String taskName, String teamName) {

        tasks.add(new Task(
                nextTaskId++,
                taskName,
                teamName
        ));

        System.out.println("Task berhasil dibuat.");
    }

    // ================= DELETE TASK =================
    @Override
    public void deleteTask(int taskId) throws TaskNotFoundException{

        Task target = getTaskById(taskId);

        if (target == null) {

            throw new TaskNotFoundException();
        }

        tasks.remove(target);

        System.out.println("Task berhasil dihapus.");
    }

    // ================= CREATE SUBTASK =================
    @Override
    public void createSubTask(int taskId,
            String subTaskName,
            String dueDate) throws TaskNotFoundException{

        Task task = getTaskById(taskId);

        if (task == null) {

            throw new TaskNotFoundException();
        }

        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate date
                = LocalDate.parse(dueDate, formatter);

        task.addSubTask(
                new SubTask(
                        nextSubTaskId++,
                        subTaskName,
                        null,
                        date
                )
        );

        System.out.println("SubTask berhasil dibuat.");
    }

    // ================= ASSIGN SUBTASK =================
    @Override
    public void assignSubTask(int subTaskId,
            String username) throws TaskNotFoundException{

        SubTask subTask = getSubTaskById(subTaskId);

        if (subTask == null) {

            throw new TaskNotFoundException();
        }

        subTask.setAssignedTo(username);
        subTask.setStatus("PENDING");

        System.out.println("SubTask berhasil diassign.");
    }

    // ================= UPDATE STATUS =================
    @Override
    public void updateSubTaskStatus(int subTaskId,
            String status) {

        SubTask subTask = getSubTaskById(subTaskId);

        if (subTask == null) {

            System.out.println("SubTask tidak ditemukan.");
            return;
        }

        subTask.setStatus(status);

        System.out.println("Status berhasil diubah.");
    }

    // ================= GET TASK =================
    public Task getTaskById(int id) {

        for (Task task : tasks) {

            if (task.getId() == id) {

                return task;
            }
        }

        return null;
    }

    // ================= GET SUBTASK =================
    public SubTask getSubTaskById(int id) {

        for (Task task : tasks) {

            for (SubTask subTask : task.getSubTasks()) {

                if (subTask.getId() == id) {

                    return subTask;
                }
            }
        }

        return null;
    }
    
    // ================= VIEW ALL TASK =================
    @Override
    public void viewTask() {

        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("\n========================================================================================================");
        System.out.printf("| %-4s | %-20s | %-20s |\n",
                "ID",
                "TASK",
                "TEAM");
        System.out.println("========================================================================================================");

        if (tasks.isEmpty()) {

            System.out.printf("| %-100s |\n", "BELUM ADA TASK");
            System.out.println("========================================================================================================");
            return;
        }

        for (Task task : tasks) {

            System.out.printf("| %-4d | %-20s | %-20s |\n",
                    task.getId(),
                    task.getTaskName(),
                    task.getTeamName());

            System.out.println("--------------------------------------------------------------------------------------------------------");

            if (task.getSubTasks().isEmpty()) {

                System.out.printf("| %-100s |\n", "BELUM ADA SUBTASK");

            } else {

                System.out.printf("| %-4s | %-25s | %-15s | %-12s | %-15s |\n",
                        "ID",
                        "SUBTASK",
                        "ASSIGNED",
                        "DUE DATE",
                        "STATUS");

                System.out.println("--------------------------------------------------------------------------------------------------------");

                for (SubTask subTask : task.getSubTasks()) {

                    String assigned = subTask.getAssignedTo() == null
                            ? "-"
                            : subTask.getAssignedTo();

                    String dueDate = subTask.getDueDate() == null
                            ? "-"
                            : subTask.getDueDate().format(formatter);

                    System.out.printf("| %-4d | %-25s | %-15s | %-12s | %-15s |\n",
                            subTask.getId(),
                            subTask.getSubTaskName(),
                            assigned,
                            dueDate,
                            subTask.getStatus());
                }
            }

            System.out.println("========================================================================================================");
        }
    }

    // ================= VIEW MY TASK =================
    @Override
        public void viewMyTask(String username) {

            DateTimeFormatter formatter
                    = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            boolean found = false;

            System.out.println("\n========================================================================================================");
            System.out.printf("| %-25s | %-20s | %-12s | %-15s |\n",
                    "SUBTASK",
                    "TASK",
                    "DUE DATE",
                    "STATUS");
            System.out.println("========================================================================================================");

            for (Task task : tasks) {

                for (SubTask subTask : task.getSubTasks()) {

                    if (subTask.getAssignedTo() != null
                            && subTask.getAssignedTo().equalsIgnoreCase(username)) {

                        found = true;

                        System.out.printf("| %-25s | %-20s | %-12s | %-15s |\n",
                                subTask.getSubTaskName(),
                                task.getTaskName(),
                                subTask.getDueDate().format(formatter),
                                subTask.getStatus());
                    }
                }
            }

            if (!found) {

                System.out.printf("| %-80s |\n", "BELUM ADA TASK");
            }

            System.out.println("========================================================================================================");
        }

    // ================= MY SUBTASK =================
        @Override
        public void listMySubTask(String username) {

            DateTimeFormatter formatter
                    = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            boolean found = false;

            System.out.println("\n============================================================================================");
            System.out.printf("| %-4s | %-25s | %-12s | %-15s |\n",
                    "ID",
                    "SUBTASK",
                    "DUE DATE",
                    "STATUS");
            System.out.println("============================================================================================");

            for (Task task : tasks) {

                for (SubTask subTask : task.getSubTasks()) {

                    if (subTask.getAssignedTo() != null
                            && subTask.getAssignedTo().equalsIgnoreCase(username)) {

                        found = true;

                        System.out.printf("| %-4d | %-25s | %-12s | %-15s |\n",
                                subTask.getId(),
                                subTask.getSubTaskName(),
                                subTask.getDueDate().format(formatter),
                                subTask.getStatus());
                    }
                }
            }

            if (!found) {

                System.out.printf("| %-70s |\n", "BELUM ADA SUBTASK");
            }

            System.out.println("============================================================================================");
        }

    // ================= GET TASK LIST =================
        public ArrayList<Task> getTasks() {

            return tasks;
        }
    }
