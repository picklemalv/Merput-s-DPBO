/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exception;

/**
 *
 * @author piannn
 */
public class UsernameAlreadyExistsException extends Exception {

    public UsernameAlreadyExistsException(String par) {

        super("Username telah digunakan!.");
    }
}