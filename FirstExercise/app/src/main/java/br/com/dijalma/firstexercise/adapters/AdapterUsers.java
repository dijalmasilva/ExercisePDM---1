package br.com.dijalma.firstexercise.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.dijalma.firstexercise.R;
import br.com.dijalma.firstexercise.model.User;

/**
 * Created by dijalma on 18/02/17.
 */

public class AdapterUsers extends BaseAdapter {

    private static int count = 1;
    private List<User> users;
    private Activity act;

    public AdapterUsers(List<User> users, Activity act) {
        this.users = users;
        this.act = act;
    }

    private void incrementCount() {
        if (count == 3) {
            count = 1;
        } else {
            count++;
        }
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.layout_users, parent, false);
        User user = users.get(position);

        ImageView image = (ImageView) view.findViewById(R.id.colorBalon);
        TextView sigla = (TextView) view.findViewById(R.id.siglaUser);
        TextView nameFriend = (TextView) view.findViewById(R.id.nameFriend);
        TextView lastMessage = (TextView) view.findViewById(R.id.lastMessage);

        if (count == 1) {
            image.setBackgroundResource(R.drawable.bg_label_1);
        } else if (count == 2) {
            image.setBackgroundResource(R.drawable.bg_label_2);
        } else {
            image.setBackgroundResource(R.drawable.bg_label_3);
        }

        incrementCount();

        sigla.setText(user.getSigla());
        nameFriend.setText(user.getName() + " " + user.getLastname());
        lastMessage.setText("E aí cara? Como vc está?!");

        return view;
    }
}
