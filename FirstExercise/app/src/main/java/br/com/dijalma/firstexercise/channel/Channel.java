package br.com.dijalma.firstexercise.channel;

import br.com.dijalma.firstexercise.model.MessageApp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="http://dijalmasilva.github.io/" target="_blank">Dijalma Silva</a> on 22/02/17 - 21:47
 */
public class Channel implements Serializable {

    //subscriber
    private int subscriber;
    //publisher
    private int publisher;

    public List<MessageApp> messages;

    public Channel(int subscriber, int publisher) {
        this.subscriber = subscriber;
        this.publisher = publisher;
        this.messages = new ArrayList<>();
    }

    public void addMessage(MessageApp message) {
        messages.add(message);
    }

    public void removeMessage(MessageApp message) {
        messages.remove(message);
    }

    public List<MessageApp> getMessages() {
        return messages;
    }

    public int getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(int subscriber) {
        this.subscriber = subscriber;
    }

    public int getPublisher() {
        return publisher;
    }

    public void setPublisher(int publisher) {
        this.publisher = publisher;
    }

    public void setMessages(List<MessageApp> messages) {
        this.messages = messages;
    }

    public boolean verificaChannel(int id, int id2) {
        if (subscriber == id) {
            if (publisher == id2) {
                return true;
            }
        }

        return false;
    }
}
