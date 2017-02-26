package br.com.dijalma.firstexercise.channel;

import br.com.dijalma.firstexercise.model.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="http://dijalmasilva.github.io/" target="_blank">Dijalma Silva</a> on 22/02/17 - 21:47
 */
public class Channel implements Serializable {

    private int myId;

    public List<Message> messages;

    public Channel(int myId) {
        this.myId = myId;
        this.messages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

}
