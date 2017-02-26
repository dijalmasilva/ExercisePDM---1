package br.com.dijalma.firstexercise.adapters;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import br.com.dijalma.firstexercise.R;
import br.com.dijalma.firstexercise.model.MessageApp;

/**
 * Created by dijalma on 20/02/17.
 */

public class AdapterMessage extends BaseAdapter {

    private List<MessageApp> messageApps;
    private Activity activity;
    private int userAuth;

    /**
     * @param messageApps
     * @param activity
     * @param userAuth
     */
    public AdapterMessage(List<MessageApp> messageApps, Activity activity, int userAuth) {
        this.messageApps = messageApps;
        Collections.sort(messageApps);
        this.activity = activity;
        this.userAuth = userAuth;
    }

    @Override
    public int getCount() {
        return messageApps.size();
    }

    @Override
    public Object getItem(int position) {
        return messageApps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return messageApps.get(position).getId();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.chatbubble, parent, false);
        MessageApp messageApp = this.messageApps.get(position);

        LinearLayout bubble_layout_parent = (LinearLayout) view.findViewById(R.id.bubble_layout_parent);
        LinearLayout bubble_layout = (LinearLayout) view.findViewById(R.id.bubble_layout);
        TextView dateOfMessage = (TextView) view.findViewById(R.id.dateOfMessage);
        TextView message_text = (TextView) view.findViewById(R.id.message_text);

        if (Objects.equals(userAuth, messageApp.getIdUser())) {
            bubble_layout.setBackgroundResource(R.drawable.bubble2);
            bubble_layout_parent.setGravity(Gravity.RIGHT);
        } else {
            bubble_layout.setBackgroundResource(R.drawable.bubble1);
            bubble_layout_parent.setGravity(Gravity.LEFT);
        }

        dateOfMessage.setText(messageApp.getDateShow());
        message_text.setText(messageApp.getMsg());
        return view;
    }

    public void setMessageApps(List<MessageApp> messageApps) {
        this.messageApps = messageApps;
    }
}
