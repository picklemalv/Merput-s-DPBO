/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import Exception.InvalidPasswordException;
import Exception.InvalidUsernameException;
import Exception.TaskNotFoundException;
import Exception.UserNotFoundException;
import Exception.UsernameAlreadyExistsException;
import java.util.Scanner;
import Service.*;
import model.*;

/**
 *
 * @author piannn
 */

public class MenuView {

    private Scanner input;

    private UserService userService;
    private TeamService teamService;
    private TaskService taskService;

    public MenuView() {

        input = new Scanner(System.in);

        userService = UserService.getInstance();
        teamService = TeamService.getInstance();
        taskService = TaskService.getInstance();
    }

    // ================= SAFE PARSE =================
    private int safeParseInt(String text) {

        try {

            return Integer.parseInt(text);

        } catch (Exception e) {

            throw new RuntimeException("Input harus berupa angka!");
        }
    }

    // ================= SHOW MENU =================
    public void showMenu(Person user) {

        while (true) {

            try {

                if (user instanceof Admin) {

                    if (adminMenu()) {
                        return;
                    }

                } else {

                    User currentUser = (User) user;

                    switch (currentUser.getRole()) {

                        case "USER":

                            if (userMenu(currentUser)) {
                                return;
                            }

                            break;

                        case "TEAM_LEADER":

                            if (leaderMenu(currentUser)) {
                                return;
                            }

                            break;

                        case "MEMBER":

                            if (memberMenu(currentUser)) {
                                return;
                            }

                            break;
                    }
                }

            } catch (Exception e) {

                System.out.println("\n❌ " + e.getMessage());
            }
        }
    }

    // ================= ADMIN MENU =================
    private boolean adminMenu()
            throws InvalidUsernameException,
            InvalidPasswordException,
            UsernameAlreadyExistsException,
            UserNotFoundException {

        System.out.println("\n==============================================================");
        System.out.println("                        ADMIN MENU");
        System.out.println("==============================================================");
        System.out.println("1. Create User");
        System.out.println("2. Delete User");
        System.out.println("3. List User");
        System.out.println("4. Logout");
        System.out.println("==============================================================");

        System.out.print("Pilih Menu : ");

        int pilih = safeParseInt(input.nextLine());

        switch (pilih) {

            case 1: {

                System.out.print("Jumlah User : ");

                int jumlah = safeParseInt(input.nextLine());

                for (int i = 1; i <= jumlah; i++) {

                    while (true) {

                        try {

                            System.out.println("\n================ USER " + i + " ================");

                            System.out.print("Username : ");
                            String username = input.nextLine();

                            System.out.print("Password : ");
                            String password = input.nextLine();

                            userService.createUser(username, password);

                            break;

                        } catch (InvalidUsernameException
                                | InvalidPasswordException
                                | UsernameAlreadyExistsException e) {

                            System.out.println(e.getMessage());
                            System.out.println("Silakan input ulang.\n");
                        }
                    }
                }

                break;
            }

            case 2: {

                userService.listUsers();

                System.out.print("\nUsername yang akan dihapus : ");

                String username = input.nextLine();

                userService.deleteUser(username);

                break;
            }

            case 3: {

                userService.listUsers();

                break;
            }

            case 4: {

                return true;
            }

            default:

                throw new RuntimeException("Pilihan menu tidak valid!");
        }

        return false;
    }

    // ================= USER MENU =================
    private boolean userMenu(User user) {

        System.out.println("\n==============================================================");
        System.out.println("                        USER MENU");
        System.out.println("==============================================================");
        System.out.println("1. Create Team");
        System.out.println("2. Join Team");
        System.out.println("3. View Team");
        System.out.println("4. Logout");
        System.out.println("==============================================================");

        System.out.print("Pilih Menu : ");

        int pilih = safeParseInt(input.nextLine());

        switch (pilih) {

            case 1: {

                System.out.print("Nama Team : ");
                String teamName = input.nextLine();

                teamService.createTeam(teamName, user.getUsername());

                user.setRole("TEAM_LEADER");

                break;
            }

            case 2: {

                if (teamService.getTeams().isEmpty()) {

                    throw new RuntimeException("Belum ada team yang tersedia!");
                }

                teamService.listTeams();

                System.out.print("\nMasukkan ID Team : ");

                int teamId = safeParseInt(input.nextLine());

                if (teamService.joinTeam(teamId, user.getUsername())) {

                    user.setRole("MEMBER");
                }

                break;
            }

            case 3: {

                teamService.listTeams();

                break;
            }

            case 4: {

                return true;
            }

            default:

                throw new RuntimeException("Pilihan menu tidak valid!");
        }

        return false;
    }

// ================= LEADER MENU =================
    private boolean leaderMenu(User user) throws TaskNotFoundException {

        Team leaderTeam = teamService.getTeamByLeader(user.getUsername());

        if (leaderTeam == null) {

            throw new RuntimeException("Anda belum memiliki team!");
        }

        System.out.println("\n==============================================================");
        System.out.println("                      LEADER MENU");
        System.out.println("==============================================================");
        System.out.println("1. View Team");
        System.out.println("2. Add Member");
        System.out.println("3. Remove Member");
        System.out.println("4. Create Task");
        System.out.println("5. Create SubTask");
        System.out.println("6. Assign SubTask");
        System.out.println("7. View Task");
        System.out.println("8. Remove Task");
        System.out.println("9. Logout");
        System.out.println("==============================================================");

        System.out.print("Pilih Menu : ");

        int pilih = safeParseInt(input.nextLine());

        switch (pilih) {

            case 1: {

                teamService.listTeams();

                break;
            }

            case 2: {

                userService.listUsers();

                System.out.print("\nUsername Member : ");

                String username = input.nextLine();

                User member = userService.getUserByUsername(username);

                if (member == null) {

                    throw new RuntimeException("User tidak ditemukan!");
                }

                if (!member.getRole().equals("USER")) {

                    throw new RuntimeException("User sudah memiliki team!");
                }

                teamService.addMember(leaderTeam.getId(), username);

                break;
            }

            case 3: {

                teamService.listMembers(leaderTeam.getId());

                System.out.print("\nUsername Member : ");

                String username = input.nextLine();

                teamService.removeMember(leaderTeam.getId(), username);

                break;
            }

            case 4: {

                System.out.print("Nama Task : ");

                String taskName = input.nextLine();

                taskService.createTask(taskName, leaderTeam.getTeamName());

                break;
            }

            case 5: {

                taskService.viewTask();

                System.out.print("\nTask ID : ");

                int taskId = safeParseInt(input.nextLine());

                System.out.print("Nama SubTask : ");

                String subTaskName = input.nextLine();

                System.out.print("Due Date (dd-MM-yyyy) : ");

                String dueDate = input.nextLine();

                taskService.createSubTask(
                        taskId,
                        subTaskName,
                        dueDate
                );

                break;
            }

            case 6: {

                if (leaderTeam.getMembers().isEmpty()) {

                    throw new RuntimeException("Belum ada member pada team.");
                }

                taskService.viewTask();

                System.out.print("\nSubTask ID : ");

                int subTaskId = safeParseInt(input.nextLine());

                SubTask subTask = taskService.getSubTaskById(subTaskId);

                if (subTask == null) {

                    throw new RuntimeException("SubTask tidak ditemukan!");
                }

                teamService.listMembers(leaderTeam.getId());

                System.out.print("\nPilih Member : ");

                int pilihMember = safeParseInt(input.nextLine());

                if (pilihMember < 1
                        || pilihMember > leaderTeam.getMembers().size()) {

                    throw new RuntimeException("Pilihan member tidak valid!");
                }

                String selectedMember
                        = leaderTeam.getMembers().get(pilihMember - 1);

                taskService.assignSubTask(
                        subTaskId,
                        selectedMember
                );

                break;
            }

            case 7: {

                taskService.viewTask();

                break;
            }
            
            case 8: {
            
                taskService.viewTask();
                
                System.out.print("\nTask ID : ");

                int taskToDelete = safeParseInt(input.nextLine());
                
                taskService.deleteTask(taskToDelete);
                
                break;
            }

            case 9: {

                return true;
            }

            default:

                throw new RuntimeException("Pilihan menu tidak valid!");
        }

        return false;
    }

    // ================= MEMBER MENU =================
    private boolean memberMenu(User user) {

        System.out.println("\n==============================================================");
        System.out.println("                      MEMBER MENU");
        System.out.println("==============================================================");
        System.out.println("1. Lihat SubTask");
        System.out.println("2. Update Status");
        System.out.println("3. Logout");
        System.out.println("==============================================================");

        System.out.print("Pilih Menu : ");

        int pilih = safeParseInt(input.nextLine());

        switch (pilih) {

            case 1: {

                taskService.listMySubTask(user.getUsername());

                break;
            }

            case 2: {

                taskService.listMySubTask(user.getUsername());

                System.out.print("\nSubTask ID : ");

                int id = safeParseInt(input.nextLine());

                SubTask subTask = taskService.getSubTaskById(id);

                if (subTask == null) {

                    throw new RuntimeException("SubTask tidak ditemukan!");
                }

                if (subTask.getAssignedTo() == null
                        || !subTask.getAssignedTo().equalsIgnoreCase(user.getUsername())) {

                    throw new RuntimeException("SubTask bukan milik anda!");
                }

                System.out.println("\nStatus yang tersedia");
                System.out.println("1. PENDING");
                System.out.println("2. IN_PROGRESS");
                System.out.println("3. DONE");

                System.out.print("Pilih Status : ");

                int pilihStatus = safeParseInt(input.nextLine());

                String status;

                switch (pilihStatus) {

                    case 1:
                        status = "PENDING";
                        break;

                    case 2:
                        status = "IN_PROGRESS";
                        break;

                    case 3:
                        status = "DONE";
                        break;

                    default:
                        throw new RuntimeException("Pilihan status tidak valid!");
                }

                taskService.updateSubTaskStatus(id, status);

                break;
            }

            case 3: {

                return true;
            }

            default:

                throw new RuntimeException("Pilihan menu tidak valid!");
        }

        return false;
    }
}
