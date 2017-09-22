package com.test.fcm;

import android.os.Handler;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.test.utils.TestСonstants;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static Handler handler;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i(TestСonstants.TAG_FCM, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Map<String, String> map = remoteMessage.getData();
            Log.i(TestСonstants.TAG_FCM, "Message data payload: " + remoteMessage.getData());
            int i = Integer.valueOf(map.get("id"));
            handler.obtainMessage(TestСonstants.HANDLER_USER_ID, i).sendToTarget();
        }
        if (remoteMessage.getNotification() != null) {
            Log.i(TestСonstants.TAG_FCM, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    public static void setHandler(Handler h) {
        handler = h;
    }


}
