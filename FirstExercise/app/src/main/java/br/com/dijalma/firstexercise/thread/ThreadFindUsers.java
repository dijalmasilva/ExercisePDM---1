package br.com.dijalma.firstexercise.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import br.com.dijalma.firstexercise.model.Publisher;
import br.com.dijalma.firstexercise.model.User;

/**
 * Created by dijalma on 24/02/17.
 */

public class ThreadFindUsers extends Thread {

    private List<User> users;
    private String email;
    private String host;

    public ThreadFindUsers(List<User> users, String email, String host) {
        this.users = users;
        this.email = email;
        this.host = host;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(host, 8081);
            new ObjectOutputStream(socket.getOutputStream()).writeObject(email);
            ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream());
            List<Publisher> publishers = null;
            publishers = (List<Publisher>) oIn.readObject();

            while (publishers == null) {
                publishers = (List<Publisher>) oIn.readObject();
            }

            for (Publisher p : publishers) {
                this.users.add(new User(p.getId(), p.getName(), p.getLastname(), p.getEmail(), p.getPassword()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
