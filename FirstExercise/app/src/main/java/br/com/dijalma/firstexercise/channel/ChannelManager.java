package br.com.dijalma.firstexercise.channel;

import br.com.dijalma.firstexercise.model.Message;
import br.com.dijalma.firstexercise.model.MessageApp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="http://dijalmasilva.github.io/" target="_blank">Dijalma Silva</a> on 23/02/17 - 21:32
 */
public class ChannelManager implements Serializable {

    private static List<Channel> channels;

    public ChannelManager() {
        ChannelManager.channels = new ArrayList<>();
    }

    public synchronized boolean addMessage(MessageApp message, int publisher, int subscriber) {
        Channel c = findChannel(subscriber, publisher);
        c.getMessages().add(message);
        return true;
    }

    public synchronized List<MessageApp> getMessages(int subscriber, int publisher) {
        Channel c = findChannel(subscriber, publisher);
        return c.getMessages();
    }

    private Channel findChannel(int subscriber, int publiser) {

        for (Channel c : ChannelManager.channels) {
            if(c.verificaChannel(subscriber, publiser)){
                return c;
            }
        }

        Channel c = new Channel(subscriber, publiser);
        ChannelManager.channels.add(c);
        return c;
    }

    private Channel createChannel(MessageApp message, int publisher, int subscriber) {
        Channel ch = new Channel(publisher, subscriber);
        ch.addMessage(message);
        return ch;
    }

    public void addChannel(Channel channel) {
        ChannelManager.channels.add(channel);
    }

    public void removeChannel(Channel channel) {
        ChannelManager.channels.remove(channel);
    }
}
