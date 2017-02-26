package br.com.dijalma.firstexercise.channel;

import br.com.dijalma.firstexercise.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="http://dijalmasilva.github.io/" target="_blank">Dijalma Silva</a> on 23/02/17 - 21:32
 */
public class ChannelManager {

    private List<Channel> channels;

    public ChannelManager() {
        this.channels = new ArrayList<>();
    }

    public synchronized boolean addMessage(Message message, int subscriber) {

        if (!channels.isEmpty()) {
            Channel c = findChannel(subscriber);
            if (c == null) {
                addChannel(createChannel(message, subscriber));
            } else {
                c.addMessage(message);
            }
        } else {
            addChannel(createChannel(message, subscriber));
        }

        return true;
    }

    public synchronized List<Message> getMessages(int myId) {
        Channel c = findChannel(myId);
        List<Message> messages = new ArrayList<>();
        int size = c.getMessages().size();
        int count = size - 1;
        for (int i = 0; i < size; i++) {
            messages.add(c.getMessages().get(count));
            c.removeMessage(c.getMessages().get(count--));
        }
        return messages;
    }

    private Channel findChannel(int myId) {

        for (Channel c : channels) {
            if (c.getMyId() == myId)
                return c;
        }

        Channel c = new Channel(myId);
        channels.add(c);
        return c;
    }

    private Channel createChannel(Message message, int subscriber) {
        Channel ch = new Channel(subscriber);
        ch.addMessage(message);
        return ch;
    }

    public void addChannel(Channel channel) {
        this.channels.add(channel);
    }

    public void removeChannel(Channel channel) {
        this.channels.remove(channel);
    }
}
