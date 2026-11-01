/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import Exception.InvalidPasswordException;
import Exception.InvalidUsernameException;
import Exception.UsernameAlreadyExistsException;
import Service.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

/**
 *
 * @author piannn
 */

public class LoginView {

    private Scanner input;
    private UserService userService;

    public LoginView() {

        input = new Scanner(System.in);
        userService = UserService.getInstance();
    }

    // ================= SAFE PARSE =================
    private int safeParseInt(String text) {

        try {

            return Integer.parseInt(text);

        } catch (Exception e) {

            throw new RuntimeException("Input harus berupa angka!");
        }
    }

    // ================= START =================
    public void start() {

        while (true) {

            try {

                System.out.println("\n======================================================================");
                System.out.println("                    PROJECT MANAGEMENT SYSTEM");
                System.out.println("======================================================================");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                System.out.println("======================================================================");

                System.out.print("Pilih Menu : ");

                int pilih = safeParseInt(input.nextLine());

                switch (pilih) {

                    case 1: {

                        login();
                        break;
                    }

                    case 2: {

                        register();
                        break;
                    }

                    case 3: {

                        System.out.println("Terima kasih telah menggunakan aplikasi.");

                        System.exit(0);
                    }

                    default:

                        throw new RuntimeException("Pilihan menu tidak valid!");
                }

            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }
    }

    // ================= LOGIN =================
    private void login() {

        System.out.println("\n==================== LOGIN ====================");

        System.out.print("Username : ");
        String username = input.nextLine();

        System.out.print("Password : ");
        String password = input.nextLine();

        Person user = userService.login(username, password);

        if (user == null) {

            throw new RuntimeException("Username atau Password salah!");
        }

        System.out.println("Login berhasil!");
        System.out.println("Selamat datang, " + user.getUsername());

        new MenuView().showMenu(user);
    }

    // ================= REGISTER =================
    private void register()
            throws InvalidUsernameException,
            InvalidPasswordException,
            UsernameAlreadyExistsException {

        while (true) {

            try {

                System.out.println("\n================== REGISTER ==================");

                System.out.print("Username : ");
                String username = input.nextLine();

                System.out.print("Password : ");
                String password = input.nextLine();

                userService.createUser(username, password);

                System.out.println("\n✅ Registrasi berhasil.");

                break;

            } catch (InvalidUsernameException
                    | InvalidPasswordException
                    | UsernameAlreadyExistsException e) {

                System.out.println(e.getMessage());
                System.out.println("Silakan coba lagi.\n");
            }
        }
    }
}
