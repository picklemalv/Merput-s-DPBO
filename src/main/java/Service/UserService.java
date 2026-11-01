/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Exception.*;
import java.util.ArrayList;
import java.util.Scanner;
import model.*;

/**
 *
 * @author piannn
 */
public class UserService {

    private static UserService instance;

    private ArrayList<Person> users;

    private UserService() {

        users = new ArrayList<>();

        users.add(
                new Admin(
                        "admin",
                        "admin123"
                )
        );
    }

    public static UserService getInstance() {

        if (instance == null) {

            instance = new UserService();
        }

        return instance;
    }

    // ================= CHECK USERNAME =================
    public boolean usernameExists(String username) {

        for (Person user : users) {

            if (user.getUsername().equalsIgnoreCase(username)) {

                return true;
            }
        }

        return false;
    }

    // ================= CREATE USER =================
    public void createUser(String username, String password)
            throws InvalidUsernameException,
            InvalidPasswordException,
            UsernameAlreadyExistsException {

        if (username == null || username.isBlank()) {

            throw new InvalidUsernameException();
        }

        if (!username.matches("[a-zA-Z0-9 ]+")) {

            throw new InvalidUsernameException();
        }

        if (password.length() < 6) {

            throw new InvalidPasswordException();
        }

        if (usernameExists(username)) {

            throw new UsernameAlreadyExistsException(
                    "Username \"" + username + "\" sudah digunakan!"
            );
        }

        users.add(
                new User(
                        username,
                        password
                )
        );

        System.out.println("User berhasil dibuat.");
    }

    // ================= DELETE USER =================
    public void deleteUser(String username)
            throws UserNotFoundException {

        Person target = null;

        for (Person user : users) {

            if (user.getUsername().equalsIgnoreCase(username)) {

                target = user;
                break;
            }
        }

        if (target == null) {

            throw new UserNotFoundException();
        }

        users.remove(target);

        System.out.println("User berhasil dihapus.");
    }

    // ================= LIST USER =================
    public void listUsers() {

        System.out.println("\n======================================================================");
        System.out.printf("| %-4s | %-25s | %-20s |\n",
                "NO",
                "USERNAME",
                "ROLE");
        System.out.println("======================================================================");

        if (users.isEmpty()) {

            System.out.printf("| %-66s |\n", "BELUM ADA USER");
            System.out.println("======================================================================");

            return;
        }

        int no = 1;

        for (Person user : users) {

            System.out.printf("| %-4d | %-25s | %-20s |\n",
                    no++,
                    user.getUsername(),
                    user.getRole());
        }

        System.out.println("======================================================================");
    }

    // ================= GET USER =================
    public User getUserByUsername(String username) {

        for (Person person : users) {

            if (person instanceof User) {

                User user = (User) person;

                if (user.getUsername().equalsIgnoreCase(username)) {

                    return user;
                }
            }
        }

        return null;
    }

    // ================= LOGIN =================
    public Person login(String username, String password) {

        for (Person user : users) {

            if (user.getUsername().equalsIgnoreCase(username)
                    && user.getPasswordHash() != null
                    && user.getPasswordHash().equals(password)) {

                return user;
            }
        }

        return null;
    }

    // ================= PROMOTE =================
    public void promoteToLeader(String username) {

        User user = getUserByUsername(username);

        if (user != null) {

            user.setRole("TEAM_LEADER");
        }
    }

    public void promoteToMember(String username) {

        User user = getUserByUsername(username);

        if (user != null) {

            user.setRole("MEMBER");
        }
    }

    public void backToUser(String username) {

        User user = getUserByUsername(username);

        if (user != null) {

            user.setRole("USER");
        }
    }

    // ================= GET ALL USER =================
    public ArrayList<Person> getUsers() {

        return users;
    }
}
