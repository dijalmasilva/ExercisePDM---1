package br.com.dijalma.firstexercise.thread;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.dijalma.firstexercise.ChatActivity;
import br.com.dijalma.firstexercise.channel.ChannelManager;
import br.com.dijalma.firstexercise.model.Message;
import br.com.dijalma.firstexercise.model.MessageApp;

/**
 * Created by dijalma on 25/02/17.
 */

public class ThreadFindNewMessages extends Thread {

    private ChannelManager manager;
    private int publish;
    private String host;

    public ThreadFindNewMessages(ChannelManager manager, int publish, String host) {
        this.manager = manager;
        this.publish = publish;
        this.host = host;
    }

    @Override
    public void run() {
        while (ChatActivity.active) {
            try {
                synchronized (this) {
                    wait();
                }
                Socket socket = new Socket(host, 10003);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(publish);
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                List<Message> messages = (List<Message>) in.readObject();
                if (messages.size() > 0) {
                    Log.d("DEBUG", "Há " + messages.size() + " mensagens novas!");
                    for (Message m : messages) {
                        MessageApp mApp = new MessageApp(m.getIdUser(), m.getMsg());
                        mApp.setDateShow(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(m.getData()));
                        manager.addMessage(mApp, publish, m.getIdUser());
                    }
                } else {
                    Log.d("DEBUG", "Não há mensagens novas!");
                }

                Thread.sleep(2000);
                synchronized (this){
                    notify();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
