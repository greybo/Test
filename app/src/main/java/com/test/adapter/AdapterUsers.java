package com.test.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.R;
import com.test.entity.Users;
import com.test.utils.TestСonstants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by m on 15.09.2017.
 */

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.ViewHolder> {
    private List<Users> list;
    private Handler handler;

    public AdapterUsers(List<Users> list, Handler handler) {
        this.list = list;
        this.handler = handler;

    }

    public Users getItem(int position) {
        return list.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Users users = list.get(position);
        holder.text.setText(users.getChangesCount() + "");
        holder.text2.setText("User login: " + users.getLogin());
        holder.text3.setText("User id: " + users.getId());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;
        @Bind(R.id.text)
        TextView text;
        @Bind(R.id.text2)
        TextView text2;
        @Bind(R.id.text3)
        TextView text3;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Users users = getItem(getPosition());
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(users.getHtmlUrl()));
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            itemView.getContext().startActivity(browserIntent);
            handler.obtainMessage(TestСonstants.HANDLER_USER_LOGIN, users.getLogin()).sendToTarget();

        }
    }
}
