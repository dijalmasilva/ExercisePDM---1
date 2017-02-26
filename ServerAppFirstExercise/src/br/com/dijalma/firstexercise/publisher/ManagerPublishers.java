package br.com.dijalma.firstexercise.publisher;

import br.com.dijalma.firstexercise.model.Publisher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="http://dijalmasilva.github.io/" target="_blank">Dijalma Silva</a> on 22/02/17 - 22:53
 */
public class ManagerPublishers {

    private List<Publisher> publishers;
    private final int PORT = 10000;

    public ManagerPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }

    public Publisher authenticated(String email, String password){
        for (Publisher p: publishers) {
            if (p.authenticated(email, password)){
                return p;
            }
        }

        return null;
    }

    public List<Publisher> getPublishersNotMe(String email){
        List<Publisher> result = new ArrayList<>();
        for(Publisher p: publishers){
            if (!p.getEmail().equals(email)){
                result.add(p);
            }
        }

        return result;
    }

    public List<Publisher> getPublishers(){
        return this.publishers;
    }
}
