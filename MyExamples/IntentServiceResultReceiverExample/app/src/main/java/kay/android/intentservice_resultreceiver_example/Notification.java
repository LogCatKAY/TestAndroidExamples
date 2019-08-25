package kay.android.intentservice_resultreceiver_example;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.Person;
import android.support.v4.app.RemoteInput;
import android.util.Log;

import java.util.Calendar;

import static kay.android.intentservice_resultreceiver_example.NotificationButtonService.EXTRA_TEXT_REPLY;

public class Notification implements NotificationButtonResultReceiver.Receiver {

    public static final String TAG = Notification.class.getSimpleName();

    public static final String CHANNEL_ID = "CHANNEL_ID";

    private int notificationCounter;

    private Context mContext;
    private NotificationManagerCompat mNotificationManager;

    private NotificationButtonResultReceiver mResultReceiver;

    public Notification(Context context) {
        mContext = context;
        mResultReceiver = new NotificationButtonResultReceiver(new Handler());
        mResultReceiver.setReceiver(this);
        mNotificationManager = NotificationManagerCompat.from(mContext);
        notificationCounter = 0;
        initChannel();
    }

    private void initChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyChannel";
            String description = "Description myChannel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void notifyMessages() {

        Intent intent = new Intent(mContext, NotificationButtonService.class);
        intent.putExtra(NotificationButtonResultReceiver.RESULT_RECEIVER, mResultReceiver);
        intent.setAction(NotificationButtonService.ACTION_ANSWER);

        PendingIntent replyPendingIntent =
                PendingIntent.getService(mContext,
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_TEXT_REPLY)
                .setLabel("Type message")
                .build();

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send,
                        "Reply", replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("MyNotification")
                .setContentText("You have notification " + notificationCounter)
                .setStyle(addMessage())
                .addAction(action);

        android.app.Notification notification = builder.build();

        mNotificationManager.notify(0, notification);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case NotificationButtonService.ANSWERED_RC:
                Log.d(TAG, "onReceiveResult: Get ANSWERED_RC");
                notificationCounter += 1;
                break;
        }
    }

    private NotificationCompat.MessagingStyle addMessage() {
        Person person = new Person.Builder().setName("You").build();
        NotificationCompat.MessagingStyle style = new NotificationCompat.MessagingStyle(person)
                .addMessage(
                        new NotificationCompat.MessagingStyle.Message("New Notification " + notificationCounter,
                                Calendar.getInstance().getTimeInMillis(), "System"));
        return style;
    }
}
