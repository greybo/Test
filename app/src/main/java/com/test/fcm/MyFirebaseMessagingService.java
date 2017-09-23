package com.test.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.test.MainActivity;
import com.test.R;
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
            if (handler == null)
                sendNotification(i);
            else
                handler.obtainMessage(TestСonstants.HANDLER_USER_ID, i).sendToTarget();
        }
        if (remoteMessage.getNotification() != null) {
            Log.i(TestСonstants.TAG_FCM, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    public static void setHandler(Handler h) {
        handler = h;
    }

    private void sendNotification(int messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id", messageBody);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Message")
                .setContentText("User id: " + messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }


}
