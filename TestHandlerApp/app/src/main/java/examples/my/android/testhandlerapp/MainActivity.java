package examples.my.android.testhandlerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CounterProcessThread.Callback {

    private Button mStartBtn;
    private Button mStopBtn;
    private CounterProcessThread mCounterProcessThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartBtn = (Button) findViewById(R.id.btn_start_handler_thread);
        mStopBtn = (Button) findViewById(R.id.btn_stop_handler_thread);

        mCounterProcessThread = new CounterProcessThread("Background ");

        //запускаем поток и инициализируем Looper
        mCounterProcessThread.start();
        mCounterProcessThread.getLooper();  // вызовется onLooperPrepared()
        mCounterProcessThread.setCallback(this);
        //теперь в фоновом потоке будет крутиться лупер и ждать задач

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounterProcessThread.startOperation();
            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounterProcessThread.stopOperation();
            }
        });


    }

    @Override
    public void sendCounter(int counter) {
        Toast.makeText(this, String.valueOf(counter), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        mCounterProcessThread.stopOperation();
        mCounterProcessThread.quitSafely();
        super.onDestroy();
    }
}
