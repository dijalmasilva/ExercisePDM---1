package br.com.dijalma.firstexercise.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by <a href="http://dijalmasilva.github.io/" target="_blank">Dijalma Silva</a> on 18/02/17 - 15:09
 */
public class Message implements Serializable {

    private int idUser;
    private String msg;
    private Date data;

    public Message(String msg, Date data, int idUser) {
        this.msg = msg;
        this.data = data;
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
