package br.com.dijalma.firstexercise.model;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

/**
 * Created by <a href="http://dijalmasilva.github.io/" target="_blank">Dijalma Silva</a> on 22/02/17 - 21:55
 */
public class Publisher implements Serializable {

    private static int count = 1;
    private int id;
    private final String email;
    private String password;
    private String name;
    private String lastname;

    public Publisher(String email, String password, String name, String lastname) {
        this.id = count++;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
    }

    public boolean authenticated(String email, String password) {
        return email.equals(this.email) && password.equals(this.password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
