package examples.my.android.testserviceapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mStartServiceBtn;
    private Button mStopServiceBtn;
    private Button mSendBroadcastBtn;
    private SimpleReceiver mSimpleReceiver;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartServiceBtn = (Button) findViewById(R.id.btn_start_service);
        mStopServiceBtn = (Button) findViewById(R.id.btn_stop_service);
        mSendBroadcastBtn = (Button) findViewById(R.id.btn_send_broadcast);

        mStartServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestService.class);
                startService(intent);
            }
        });

        mStopServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestService.class);
                stopService(intent);
            }
        });

        mSendBroadcastBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sendBroadcast(new Intent(SimpleReceiver.SIMPLE_ACTION));
            }
        });

        mSimpleReceiver = new SimpleReceiver();
        mIntentFilter = new IntentFilter(SimpleReceiver.SIMPLE_ACTION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mSimpleReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mSimpleReceiver);
    }
}
