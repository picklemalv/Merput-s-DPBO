/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author piannn
 */
public class Admin extends Person {
    public Admin(String username, String passwordHash) {
        super(username, passwordHash);
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }
}
