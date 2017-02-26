package br.com.dijalma.firstexercise.thread;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.com.dijalma.firstexercise.model.Message;
import br.com.dijalma.firstexercise.objectsVO.MessageVO;

/**
 * Created by dijalma on 25/02/17.
 */

public class ThreadSendMessage extends Thread {

    private Message message;
    private int idSubscriber;
    private String host;

    public ThreadSendMessage(Message message, int idSubscriber, String host) {
        this.message = message;
        this.idSubscriber = idSubscriber;
        this.host = host;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(host, 10002);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            MessageVO messageVO = new MessageVO(idSubscriber, message);
            out.writeObject(messageVO);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            String result = "";
            while (result.equals("")){
                result = (String) in.readObject();
            }
            Log.d("DEBUG", result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
