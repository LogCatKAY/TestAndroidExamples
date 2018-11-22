package examples.my.android.testhandlerapp;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

// пусть в этом потоке по нажатию на кнопку запускается счетчик
// и в mainthread выводится тост с числами
public class CounterProcessThread extends HandlerThread {

    private static final int MESSAGE_START_BUTTON_CLICKED = 0;
    private static final int MESSAGE_STOP_BUTTON_CLICKED = 1;

    private Handler mMainHandler;
    private Handler mBackgroundHandler;
    private Callback mCallback;         //наш интерфейс - что отправлять или получать

    private int mCounter = 0;
    private boolean isRun = false;


    // конструктор требует наследование
    public CounterProcessThread(String name) {
        super(name);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void sendCounter(int counter);
    }

    @SuppressLint("HandlerLeak")    //находимся в handler thread, утечки не будет
    @Override
    protected void onLooperPrepared() {

        // создаем хендлер, связанный с мейнтредом
        mMainHandler = new Handler(Looper.getMainLooper());

        // создаем хендлер, связанный с текущим тредом
        mBackgroundHandler = new Handler() {

            //и указываем ему, что делать в случае получения сообщения с what значением

            // это будет выполнено в фоновом потоке

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_START_BUTTON_CLICKED:
                        if (!isRun) {
                            isRun = true;
                            processCounter();
                        }
                        break;
                    case MESSAGE_STOP_BUTTON_CLICKED:
                        isRun = false;
                        break;
                }
                try {
                    msg.recycle(); //it can work in some situations
                } catch (IllegalStateException e) {
                    this.removeMessages(msg.what); //if recycle doesnt work we do it manually
                }
            }
        };
    }

    private void processCounter() {

            // постим в main thread через mainHandler
            mMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //этот код выполняется уже в главном потоке
                    if(isRun) {
                        mCounter++;
                        mCallback.sendCounter(mCounter);
                        processCounter();
                    }
                }
            }, 4000);
    }

    //этот метод будет вызываться из главного потока
    public void startOperation() {
         //создаем message от backgroundHandler'а и отправляем в очередь
        mBackgroundHandler
                .obtainMessage(MESSAGE_START_BUTTON_CLICKED)
                .sendToTarget();
        // созданное сообщение попадает в очередь фонового потока
        // т.к. хендлер связан с лупером фонового потока
        // сейчас вызовется метод handleMessage,
        // который переопределен выше в методе onLooperPrepared
    }

    public void stopOperation() {
        mBackgroundHandler
                .obtainMessage(MESSAGE_STOP_BUTTON_CLICKED)
                .sendToTarget();
    }
}
