package examples.my.android.testserviceapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SimpleReceiver extends BroadcastReceiver {

    public static final String SIMPLE_ACTION = "examples.my.android.testserviceapp.SIMPLE_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        long time = intent.getLongExtra(TestService.TIME, 0L);
        Toast.makeText(context, "current time is " + time, Toast.LENGTH_SHORT).show();

    }
}
