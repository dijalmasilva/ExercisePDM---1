package br.com.dijalma.firstexercise.threads;

import br.com.dijalma.firstexercise.objectsVO.LoginVO;
import br.com.dijalma.firstexercise.publisher.ManagerPublishers;
import br.com.dijalma.firstexercise.model.Publisher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by <a href="http://dijalmasilva.github.io/" target="_blank">Dijalma Silva</a> on 23/02/17 - 16:42
 */
public class ThreadLogin extends Thread {

    private Socket socket;
    private ManagerPublishers manager;

    public ThreadLogin(Socket socket, ManagerPublishers manager) {
        this.socket = socket;
        this.manager = manager;
    }

    @Override
    public void run() {
        ObjectInputStream oIn = null;
        try {
            oIn = new ObjectInputStream(socket.getInputStream());
            LoginVO login = null;
            while (login == null) {
                System.out.println("Não recebeu ainda o objeto...");
                login = (LoginVO) oIn.readObject();
                System.out.println(login);
            }
            System.out.println("Objeto recebido!");
            System.out.println("email: " + login.getEmail());
            Publisher p = manager.authenticated(login.getEmail(), login.getPassword());
            ObjectOutputStream oOut = new ObjectOutputStream(socket.getOutputStream());
            if (p != null) {
                oOut.writeObject(p);
                System.out.println("Enviando objeto encontrado");
            } else {
                oOut.writeObject(new Publisher("", "", "", ""));
                System.out.println("Enviando objeto vazio");
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
