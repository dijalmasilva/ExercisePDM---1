package br.com.dijalma.firstexercise.threads;

import br.com.dijalma.firstexercise.channel.ChannelManager;
import br.com.dijalma.firstexercise.model.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by <a href="http://dijalmasilva.github.io/" target="_blank">Dijalma Silva</a> on 24/02/17 - 01:06
 */
public class ThreadGetMessages extends Thread {

    private Socket socket;
    private ChannelManager channelManager;

    public ThreadGetMessages(Socket socket, ChannelManager channelManager) {
        this.socket = socket;
        this.channelManager = channelManager;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream());
            Integer id = null;
            id = (Integer) oIn.readObject();

            while(id == null){
                id = (Integer) oIn.readObject();
            }

            List<Message> messages = channelManager.getMessages(id);
            ObjectOutputStream oOut = new ObjectOutputStream(socket.getOutputStream());
            oOut.writeObject(messages);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
