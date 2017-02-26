package br.com.dijalma.firstexercise;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.dijalma.firstexercise.adapters.AdapterUsers;
import br.com.dijalma.firstexercise.channel.ChannelManager;
import br.com.dijalma.firstexercise.model.User;
import br.com.dijalma.firstexercise.thread.ThreadFindUsers;

public class HomeActivity extends Activity {

    private User userAuth;
    private ChannelManager channelManager;

    private void notFunctionAvailable() {
        Context context = getApplicationContext();
        CharSequence text = "Funcionalidade ainda não disponível!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 200);
        toast.show();
    }

    private List<User> findUsers(final String email) {
        final List<User> users = new ArrayList<>();
        final String host = getString(R.string.host);


        final ThreadFindUsers findUsers = new ThreadFindUsers(users, email, host);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findUsers.start();
            }
        });
        while (users.size() <= 0) {

        }

        return users;
    }

    private void callTheChat(User userFriend) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("userFriend", userFriend);
        intent.putExtra("userAuth", userAuth);
        intent.putExtra("channel", channelManager);
        startActivity(intent);
    }

    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle extras = getIntent().getExtras();
        userAuth = (User) extras.get("userAuth");
        channelManager = (ChannelManager) extras.get("channel");
        if (channelManager == null){
            channelManager = new ChannelManager();
        }

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Olá " + userAuth.getName() + " " + userAuth.getLastname());
        actionBar.setIcon(R.drawable.logo);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView nameUserAuth = (TextView) findViewById(R.id.nameUser);
        nameUserAuth.setText(userAuth.getName().toUpperCase() + " " + userAuth.getLastname().toUpperCase());
        final Button btTabChat = (Button) findViewById(R.id.btTabChat);
        final Button btTabMission = (Button) findViewById(R.id.btTabMission);
        final Button btNewMission = (Button) findViewById(R.id.btNewMission);
        final Button btLastMissions = (Button) findViewById(R.id.btLastMissions);
        final Button btSettings = (Button) findViewById(R.id.btSettings);
        final Button btGallery = (Button) findViewById(R.id.btGallery);
        final View tabChat = findViewById(R.id.tabChat);
        final View tabMission = findViewById(R.id.tabMission);
        final ListView listFriends = (ListView) findViewById(R.id.friends);

        final List<User> users = findUsers(userAuth.getEmail());
        AdapterUsers adapterUsers = new AdapterUsers(users, this);

        listFriends.setAdapter(adapterUsers);

        btTabChat.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                tabMission.setVisibility(View.GONE);
                btTabMission.setBackgroundColor(Color.parseColor("#88000000"));
                tabChat.setVisibility(View.VISIBLE);
                btTabChat.setBackgroundColor(Color.parseColor("#88f9f9f9"));
            }
        });

        btTabMission.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabChat.setVisibility(View.GONE);
                btTabChat.setBackgroundColor(Color.parseColor("#88000000"));
                tabMission.setVisibility(View.VISIBLE);
                btTabMission.setBackgroundColor(Color.parseColor("#88f9f9f9"));
            }
        });

        listFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User clicked = users.get(position);
                ChatActivity.active = true;
                callTheChat(clicked);
            }
        });

        btNewMission.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                notFunctionAvailable();
            }
        });

        btLastMissions.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                notFunctionAvailable();
            }
        });

        btSettings.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                notFunctionAvailable();
            }
        });

        btGallery.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                notFunctionAvailable();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
