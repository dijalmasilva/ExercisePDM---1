package br.com.dijalma.firstexercise;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.com.dijalma.firstexercise.channel.ChannelManager;
import br.com.dijalma.firstexercise.model.Publisher;
import br.com.dijalma.firstexercise.model.User;
import br.com.dijalma.firstexercise.objectsVO.LoginVO;

public class MainActivity extends Activity {

    private ChannelManager channelManager;

    private void login(final EditText email, final EditText password) {
        final String emailUser = email.getText().toString();
        final String passwordUser = password.getText().toString();
        final User[] user = {null};
        Runnable run = new Runnable() {
            @Override
            public void run() {
                user[0] = request(emailUser, passwordUser);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (user[0] != null) {
                            callLayoutHome(user[0]);
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Email ou senha inválidos!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 200);
                            toast.show();
                        }
                    }
                });
            }
        };

        Thread thread = new Thread(run);
        thread.start();
    }

    private User request(String email, String password) {
        User user = null;
        int hostInt = R.string.host;
        String host = getString(hostInt);
        Log.d("DEBUG", "HOST ENCONTRADO: " + host);
        try {
            Socket socket = new Socket(host, 10000);
            ObjectOutputStream oOut = new ObjectOutputStream(socket.getOutputStream());
            LoginVO loginVO = new LoginVO(email, password);
            oOut.writeObject(loginVO);
            ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream());
            Publisher p = null;
            p = (Publisher) oIn.readObject();
            while (p == null) {
                p = (Publisher) oIn.readObject();
            }
            if (!p.getEmail().equals("")) {
                user = new User(p.getId(), p.getName(), p.getLastname(), p.getEmail(), p.getPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return user;
    }

    private void callLayoutHome(User user) {
        Log.d("DEBUG", user.getEmail() + " entrou!");
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("userAuth", user);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        final EditText emailLogin = (EditText) findViewById(R.id.emailLogin);
        final EditText passwordLogin = (EditText) findViewById(R.id.passwordLogin);
        Button btEntrar = (Button) findViewById(R.id.btEntrar);


        btEntrar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(emailLogin, passwordLogin);
            }
        });
    }
}
