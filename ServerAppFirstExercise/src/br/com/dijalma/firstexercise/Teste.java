package br.com.dijalma.firstexercise;

import br.com.dijalma.firstexercise.model.Message;
import br.com.dijalma.firstexercise.model.Publisher;
import br.com.dijalma.firstexercise.objectsVO.MessageVO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

/**
 * Created by <a href="http://dijalmasilva.github.io/" target="_blank">Dijalma Silva</a> on 23/02/17 - 15:42
 */
public class Teste {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        int i = 0;
//        while(i++ < 4) {
//
//            if (i == 2) {
//
        try (Socket s = new Socket("localhost", 8081)) {
            String email = "dijalmacz@gmail.com";
            ObjectOutputStream oOut = new ObjectOutputStream(s.getOutputStream());
            oOut.writeObject(email);
            ObjectInputStream oIn = new ObjectInputStream(s.getInputStream());
            List<Publisher> publishers = null;
            publishers = (List<Publisher>) oIn.readObject();
            while (publishers == null) {
                publishers = (List<Publisher>) oIn.readObject();
            }

            for (Publisher p : publishers) {
                System.out.println(p.getName());
            }
            System.out.println("Lista recebida contendo " + publishers.size() + " publishers");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//
//
//            try (Socket socket = new Socket("localhost", 10000)) {
//                System.out.println("Conectou-se com o servidor!");
//                LoginVO loginVO = new LoginVO("dijalmacz@gmail.com", "123");
//                ObjectOutputStream oOut = new ObjectOutputStream(socket.getOutputStream());
//                oOut.writeObject(loginVO);
//                System.out.println("Enviou objeto de login!");
//                ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream());
//                Publisher p = null;
//                while (p == null) {
//                    System.out.println("Não recebeu o objeto do servidor!");
//                    p = (Publisher) oIn.readObject();
//                    System.out.println(p);
//                }
//                System.out.println("Objeto de resposta recebido!");
//                if (p.getEmail().equals("")) {
//                    System.out.println("Usuário ou senha inválidos!");
//                } else {
//                    System.out.println(p.getName() + " " + p.getLastname() + " entrou!");
//                }
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }


//        Message message = new Message("E aí cara?", new Date(), 2);
//        MessageVO messageVO1 = new MessageVO(1, message);
//        Message message2 = new Message("Opa", new Date(), 1);
//        MessageVO messageVO2 = new MessageVO(2, message2);
//        Message message3 = new Message("Nada não!", new Date(), 2);
//        MessageVO messageVO3 = new MessageVO(1, message3);


//        Socket socket = new Socket("localhost", 10002);
//        ObjectOutputStream oOut = new ObjectOutputStream(socket.getOutputStream());
//        oOut.writeObject(messageVO1);
//        ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream());
//        String result = null;
//        result = (String) oIn.readObject();
//        while (result == null){
//            result = (String) oIn.readObject();
//        }
//        System.out.println(result);
//
//        socket = new Socket("localhost", 10002);
//        oOut = new ObjectOutputStream(socket.getOutputStream());
//        oOut.writeObject(messageVO2);
//        oIn = new ObjectInputStream(socket.getInputStream());
//        result = null;
//        result = (String) oIn.readObject();
//        while (result == null){
//            result = (String) oIn.readObject();
//        }
//        System.out.println(result);
//
//        socket = new Socket("localhost", 10002);
//        oOut = new ObjectOutputStream(socket.getOutputStream());
//        oOut.writeObject(messageVO3);
//        oIn = new ObjectInputStream(socket.getInputStream());
//        result = null;
//        result = (String) oIn.readObject();
//        while (result == null){
//            result = (String) oIn.readObject();
//        }
//        System.out.println(result);

//        Socket socket = new Socket("localhost", 10003);
//        ObjectOutputStream oOut = new ObjectOutputStream(socket.getOutputStream());
//        oOut.writeObject(new Integer(1));
//        ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream());
//        List<Message> messages = null;
//        messages = (List<Message>) oIn.readObject();
//        while (messages == null){
//            messages = (List<Message>) oIn.readObject();
//        }
//        System.out.println("Quantidade de mensagens: " + messages.size());
//
//        socket = new Socket("localhost", 10003);
//        oOut = new ObjectOutputStream(socket.getOutputStream());
//        oOut.writeObject(new Integer(2));
//        oIn = new ObjectInputStream(socket.getInputStream());
//        messages = null;
//        messages = (List<Message>) oIn.readObject();
//        while (messages == null){
//            messages = (List<Message>) oIn.readObject();
//        }
//        System.out.println("Quantidade de mensagens: " + messages.size());
//
//        socket.close();
//    }
}
