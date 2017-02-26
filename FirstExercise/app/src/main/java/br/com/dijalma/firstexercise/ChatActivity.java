package br.com.dijalma.firstexercise;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

import br.com.dijalma.firstexercise.adapters.AdapterMessage;
import br.com.dijalma.firstexercise.channel.ChannelManager;
import br.com.dijalma.firstexercise.model.Message;
import br.com.dijalma.firstexercise.model.MessageApp;
import br.com.dijalma.firstexercise.model.User;
import br.com.dijalma.firstexercise.thread.ThreadFindNewMessages;
import br.com.dijalma.firstexercise.thread.ThreadSendMessage;

public class ChatActivity extends Activity {

    private User userAuth;
    private ChannelManager channelManager;
    private String host;
    public static boolean active;
    private ThreadFindNewMessages threadFindNewMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle extras = getIntent().getExtras();
        userAuth = (User) extras.get("userAuth");
        final User userFriend = (User) extras.get("userFriend");
        channelManager = (ChannelManager) extras.get("channel");
        if (channelManager == null) {
            channelManager = new ChannelManager();
        }

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(userFriend.getName() + " " + userFriend.getLastname());
        actionBar.setSubtitle("status indisponível");
        actionBar.setLogo(R.drawable.logo);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        final List<MessageApp> messagesApps = channelManager.getMessages(userFriend.getId(), userAuth.getId());

        final EditText message = (EditText) findViewById(R.id.etMessage);
        ImageButton send = (ImageButton) findViewById(R.id.btSend);

        final ListView list_messages = (ListView) findViewById(R.id.list_messages);
        final AdapterMessage adapterMessage = new AdapterMessage(messagesApps, this, userAuth.getId());

        list_messages.setAdapter(adapterMessage);
        list_messages.setSelection(list_messages.getCount() - 1);
        host = getString(R.string.host);
        send.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = message.getText().toString();
                if (msg.length() != 0) {
                    final MessageApp messageApp = new MessageApp(userAuth.getId(), msg);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Message message1 = new Message(messageApp.getMsg(), messageApp.getDateMessage(), messageApp.getIdUser());
                            new ThreadSendMessage(message1, userFriend.getId(), host).start();
                        }
                    });
                    channelManager.addMessage(messageApp, userAuth.getId(), userFriend.getId());
                    message.setText("");
                    adapterMessage.setMessageApps(channelManager.getMessages(userFriend.getId(), userAuth.getId()));
                    list_messages.setAdapter(adapterMessage);
                    list_messages.setSelection(list_messages.getCount() - 1);
                }
            }
        });

        threadFindNewMessages = new ThreadFindNewMessages(channelManager, userAuth.getId(), host);
        threadFindNewMessages.start();


        Thread t = new Thread() {
            @Override
            public void run() {
                while (ChatActivity.active) {
                    synchronized (threadFindNewMessages){
                        threadFindNewMessages.notify();
                    }
                    synchronized (threadFindNewMessages){
                        try {
                            threadFindNewMessages.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapterMessage.setMessageApps(channelManager.getMessages(userFriend.getId(), userAuth.getId()));
                            list_messages.setAdapter(adapterMessage);
                            list_messages.setSelection(list_messages.getCount() - 1);
                        }
                    });
                    Log.d("DEBUG", "ATUALIZOU MENSAGENS!");
                }
            }
        };

        t.start();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ChatActivity.active = false;
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("userAuth", userAuth);
        intent.putExtra("channel", channelManager);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}