/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exception;

/**
 *
 * @author piannn
 */
public class InvalidPasswordException extends Exception {

    public InvalidPasswordException() {

        super("Password minimal 6 karakter!");
    }
}