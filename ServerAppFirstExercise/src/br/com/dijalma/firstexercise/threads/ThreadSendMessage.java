package br.com.dijalma.firstexercise.threads;

import br.com.dijalma.firstexercise.channel.ChannelManager;
import br.com.dijalma.firstexercise.objectsVO.MessageVO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by <a href="http://dijalmasilva.github.io/" target="_blank">Dijalma Silva</a> on 24/02/17 - 00:40
 */
public class ThreadSendMessage extends Thread {

    private Socket socket;
    private ChannelManager manager;

    public ThreadSendMessage(Socket socket, ChannelManager manager) {
        this.socket = socket;
        this.manager = manager;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream());
            MessageVO messageVO = null;
            messageVO = (MessageVO) oIn.readObject();
            while (messageVO == null){
                messageVO = (MessageVO) oIn.readObject();
            }
            boolean b = manager.addMessage(messageVO.getMessage(), messageVO.getSubscriber());
            ObjectOutputStream oOut = new ObjectOutputStream(socket.getOutputStream());
            if (b)
                oOut.writeObject("MENSAGEM ENVIADA!");
            else
                oOut.writeObject("FALHA NO ENVIO DA MENSAGEM!");

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
