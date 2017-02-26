package br.com.dijalma.firstexercise.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dijalma on 20/02/17.
 */

public class MessageApp implements Comparable<MessageApp> {

    private static int count = 0;
    private int id;
    private int idUser;
    private String msg;
    private final Date dateMessage;
    private String dateShow;
    //VIEW, UNVIEW
    private String status;

    /**
     * @param idUser
     * @param msg
     */
    public MessageApp(int idUser, String msg) {
        this.id = ++count;
        this.idUser = idUser;
        this.msg = msg;
        this.status = "UNVIEW";
        this.dateMessage = new Date();
        this.dateShow = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDateShow() {
        return dateShow;
    }

    public void setDateShow(String dateShow) {
        this.dateShow = dateShow;
    }

    public Date getDateMessage() {
        return dateMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus() {
        if (status.equals("UNVIEW")) {
            status = "VIEW";
        }
    }

    @Override
    public int compareTo(MessageApp m) {
        return this.getDateMessage().compareTo(m.getDateMessage());
    }
}
