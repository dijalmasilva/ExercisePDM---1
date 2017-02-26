package br.com.dijalma.firstexercise.objectsVO;

import java.io.Serializable;

import br.com.dijalma.firstexercise.model.Message;

/**
 * Created by <a href="http://dijalmasilva.github.io/" target="_blank">Dijalma Silva</a> on 24/02/17 - 01:00
 */
public class MessageVO implements Serializable {

    private int subscriber;
    private Message message;

    public MessageVO(int subscriber, Message message) {
        this.subscriber = subscriber;
        this.message = message;
    }

    public int getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(int subscriber) {
        this.subscriber = subscriber;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
