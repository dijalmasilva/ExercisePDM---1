package br.com.dijalma.firstexercise;

import br.com.dijalma.firstexercise.channel.ChannelManager;
import br.com.dijalma.firstexercise.publisher.ManagerPublishers;
import br.com.dijalma.firstexercise.model.Publisher;
import br.com.dijalma.firstexercise.threads.ThreadGetMessages;
import br.com.dijalma.firstexercise.threads.ThreadLogin;
import br.com.dijalma.firstexercise.threads.ThreadSendMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="http://dijalmasilva.github.io/" target="_blank">Dijalma Silva</a> on 22/02/17 - 22:50
 */
public class Loader {

    public static void main(String[] args){

        List<Publisher> publishers = new ArrayList<>();
        publishers.add(new Publisher("dijalmacz@gmail.com", "123", "Dijalma", "Silva"));
        publishers.add(new Publisher("bruna@gmail.com", "1234", "Bruna", "Sampaio"));
        publishers.add(new Publisher("joao@gmail.com", "12345", "João", "Marcos"));

        ManagerPublishers managerPublishers = new ManagerPublishers(publishers);
        ChannelManager channelManager = new ChannelManager();

        Thread servicesLogin = new Thread(){
            @Override
            public void run() {
                try {
                    servicoDeLogin(managerPublishers);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread servicesListPublishers = new Thread(){
            @Override
            public void run() {
                try {
                    servicoDeCarregarPublishers(managerPublishers);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread servicesSendMessage = new Thread(){
            @Override
            public void run() {
                try {
                    servicoDeEnviarMensagens(channelManager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread servicesGetMessages = new Thread(){
            @Override
            public void run() {
                try {
                    servicoDeRecuperarMensagensNovas(channelManager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        servicesLogin.start();
        servicesListPublishers.start();
        servicesSendMessage.start();
        servicesGetMessages.start();
    }

    private static void servicoDeLogin(ManagerPublishers managerPublishers) throws IOException {
        ServerSocket server = new ServerSocket(10000);
        while (true) {
            Socket socket = server.accept();
            System.out.println("Login requisitado!");
            ThreadLogin login = new ThreadLogin(socket, managerPublishers);
            login.start();
        }
    }

    private static void servicoDeCarregarPublishers(ManagerPublishers managerPublishers) throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket(8081);
        while (true) {
            Socket socket = server.accept();
            System.out.println("Publishers requisitados!");
            ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream());
            String email = null;
            email = (String) oIn.readObject();
            while(email == null){
                email = (String) oIn.readObject();
            }
            ObjectOutputStream oOut = new ObjectOutputStream(socket.getOutputStream());
            oOut.writeObject(managerPublishers.getPublishersNotMe(email));
            socket.close();
        }
    }

    private static void servicoDeEnviarMensagens(ChannelManager channelManager) throws IOException {
        ServerSocket server = new ServerSocket(10002);
        while(true){
            Socket socket = server.accept();
            System.out.println("Servico de enviar mensagem requisitado!");
            ThreadSendMessage sendMessage = new ThreadSendMessage(socket, channelManager);
            sendMessage.start();
        }
    }

    private static void servicoDeRecuperarMensagensNovas(ChannelManager channelManager) throws IOException {
        ServerSocket server = new ServerSocket(10003);
        while(true){
            Socket socket = server.accept();
            System.out.println("Servico de recuperar mensagens novas requisitado!");
            ThreadGetMessages getMessages = new ThreadGetMessages(socket, channelManager);
            getMessages.start();
        }
    }
}
