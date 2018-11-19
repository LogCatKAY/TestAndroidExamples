package examples.my.android.testserviceapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestService extends Service {

    private static final String TAG = TestService.class.getSimpleName();
    public static final String TIME = "TIME";
    private ScheduledExecutorService mScheduledExecutors;
    private String[] str = {"раз", "два", "три", "четыре", "пять"};
    private NotificationManager mManager;
    private NotificationCompat.Builder mBuilder;

    public TestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        mScheduledExecutors = Executors.newScheduledThreadPool(1);

        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = getNotificationBuilder();

        mBuilder.setContentTitle("SimpleService notification")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
    }

    private NotificationCompat.Builder getNotificationBuilder() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return new NotificationCompat.Builder(this);
        } else {
            String channelId = "my_channel_id";

            if(mManager.getNotificationChannel(channelId) == null) {
                NotificationChannel channel = new NotificationChannel(channelId, "Text for user", NotificationManager.IMPORTANCE_LOW);
                mManager.createNotificationChannel(channel);
            }

            return new NotificationCompat.Builder(this, channelId);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");

        startForeground(123, getNotification("Start notification"));

        mScheduledExecutors.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < str.length; i++) {
                    Log.d(TAG, str[i]);
                }
                mManager.notify(123, getNotification("time is " + System.currentTimeMillis()));
                Intent intentToSend = new Intent(SimpleReceiver.SIMPLE_ACTION);
                intentToSend.putExtra(TIME, System.currentTimeMillis());
                sendBroadcast(intentToSend);

            }
        }, 1000, 4000, TimeUnit.MILLISECONDS);

        return START_STICKY;
    }

    private Notification getNotification(String contentText) {
        return mBuilder.setContentText(contentText).build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScheduledExecutors.shutdownNow();
        Log.d(TAG, "onDestroy: ");
    }


}
