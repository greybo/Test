package com.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.messaging.FirebaseMessaging;
import com.test.adapter.AdapterUsers;
import com.test.api.Manager;
import com.test.entity.Users;
import com.test.fcm.MyFirebaseMessagingService;
import com.test.utils.TestСonstants;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Manager managerApi;
    private RecyclerView recyclerView;
    private AdapterUsers adapter;
    private EditText textUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textUserId = (EditText) findViewById(R.id.textLogin);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        managerApi = new Manager(handler);
        if (getIntent().getExtras() != null) {
            int id = getIntent().getIntExtra("id", 0);
            managerApi.getUsersDao().changesCount(id);
        }
        if (managerApi.getUsersDao().count() == 0)
            managerApi.getUsers();
        else
            managerApi.getUsersDao().readAll();
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managerApi.send(Integer.valueOf(textUserId.getText().toString()));
            }
        });
    }

    private void createAdapter(List<Users> list) {
        adapter = new AdapterUsers(list, handler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TestСonstants.HANDLER_USERS_LIST:
                    createAdapter((List<Users>) msg.obj);
                    break;
                case TestСonstants.HANDLER_USER_ID:
                    managerApi.getUsersDao().changesCount((int) msg.obj);
                    adapter.notifyDataSetChanged();
                    break;
                case TestСonstants.HANDLER_USER_LOGIN:
                    managerApi.getDataGit((String) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        managerApi.realmClose();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyFirebaseMessagingService.setHandler(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyFirebaseMessagingService.setHandler(handler);
    }
}
