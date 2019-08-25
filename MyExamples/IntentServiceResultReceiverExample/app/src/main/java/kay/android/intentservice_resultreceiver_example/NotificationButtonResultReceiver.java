package kay.android.intentservice_resultreceiver_example;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class NotificationButtonResultReceiver extends ResultReceiver {

    public static final String RESULT_RECEIVER = "resultReceiver";

    private Receiver mReceiver;

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }

    public void setReceiver(Receiver receiver) {
        this.mReceiver = receiver;
    }

    public NotificationButtonResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
