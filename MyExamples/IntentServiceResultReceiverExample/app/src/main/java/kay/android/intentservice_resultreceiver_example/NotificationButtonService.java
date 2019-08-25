package kay.android.intentservice_resultreceiver_example;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.Person;
import android.support.v4.app.RemoteInput;

import java.util.Calendar;

public class NotificationButtonService extends IntentService {
    public static final String ACTION_ANSWER = "kay.android.intentservice_resultreceiver_example.action.ACTION_ANSWER";
    public static final String EXTRA_TEXT_REPLY = "extraTextReplyRemoteInput";

    public static final int ANSWERED_RC = 1;


    public NotificationButtonService() {
        super("NotificationButtonService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final ResultReceiver receiver =
                    intent.getParcelableExtra(NotificationButtonResultReceiver.RESULT_RECEIVER);

            final String action = intent.getAction();
            switch (action) {
                case ACTION_ANSWER:
                    handleActionAnswer(intent);
                    receiver.send(ANSWERED_RC, Bundle.EMPTY);
                    break;
            }
        }
    }

    private void handleActionAnswer(Intent intent) {
        // Get reply text
        CharSequence replyText = null;
        Bundle results = RemoteInput.getResultsFromIntent(intent);
        if (results != null) {
            replyText = results.getCharSequence(EXTRA_TEXT_REPLY);
        }


        Notification activeNotification = findActiveNotification(0);
        NotificationCompat.MessagingStyle activeStyle = NotificationCompat.MessagingStyle.extractMessagingStyleFromNotification(activeNotification);
        Notification.Builder recoveredBuilder = Notification.Builder.recoverBuilder(getBaseContext(), activeNotification);

        Person person = new Person.Builder().setName("You").build();
        NotificationCompat.MessagingStyle newStyle = new NotificationCompat.MessagingStyle(person);

        for (NotificationCompat.MessagingStyle.Message message : activeStyle.getMessages()) {
            newStyle.addMessage(message);
        }

        newStyle.addMessage(new NotificationCompat.MessagingStyle.Message(replyText,
                Calendar.getInstance().getTimeInMillis(), person));

        //recoveredBuilder.setStyle(newStyle);

        Notification repliedNotification =
                new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText("Replied")
                        .setStyle(newStyle)
                        .build();

        // Update notification
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(getBaseContext().NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, repliedNotification);
    }

    private NotificationCompat.MessagingStyle addLine(String replyText) {

        Person person = new Person.Builder().setName("You").build();
        NotificationCompat.MessagingStyle style = new NotificationCompat.MessagingStyle(person)
                .addMessage(
                        new NotificationCompat.MessagingStyle.Message(replyText,
                                Calendar.getInstance().getTimeInMillis(), person));
        return style;
    }

    private Notification findActiveNotification(int notificationId) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(getBaseContext().NOTIFICATION_SERVICE);
        StatusBarNotification[] notifications = mNotificationManager.getActiveNotifications();
        for (int i = 0; i < notifications.length; i++){
            if (notificationId == notifications[i].getId()) {
                return notifications[i].getNotification();
            }
        }
        return null;
    }
}
