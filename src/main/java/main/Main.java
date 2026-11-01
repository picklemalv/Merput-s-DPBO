/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import Exception.InvalidPasswordException;
import Exception.InvalidUsernameException;
import view.*;

/**
 *
 * @author piannn1
 */
public class Main {
    public static void main(String[] args) throws InvalidUsernameException, InvalidPasswordException {

        LoginView login = new LoginView();

        login.start();
    }
}