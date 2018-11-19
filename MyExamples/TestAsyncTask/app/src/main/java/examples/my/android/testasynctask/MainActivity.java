package examples.my.android.testasynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private Button mDownloadButton;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.iv_image);
        mDownloadButton = (Button) findViewById(R.id.btn_start_download);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url =
                        "http://www.kartinkijane.ru/pic/201305/1280x800/kartinkijane.ru-39561.jpg";
                new AsyncDownloadTask(MainActivity.this).execute(url);
            }
        });


    }

    public ImageView getImageView() {
        return mImageView;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    private static class AsyncDownloadTask extends AsyncTask<String, Void, Bitmap> {

        // чтобы избежать утечек при переворотах устройства - используется слабая ссылка
        private WeakReference<MainActivity> mActivityWeakReference;

        private AsyncDownloadTask(MainActivity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            MainActivity activity = mActivityWeakReference.get();
            if(activity != null) {
                activity.getProgressBar().setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return getBitmapFromUrl(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            MainActivity activity = mActivityWeakReference.get();
            if(activity != null) {
                activity.getImageView().setImageBitmap(bitmap);
                activity.getProgressBar().setVisibility(View.INVISIBLE);
            }
        }

        // только в качестве примера - лучше использовать библиотеку для загрузки изображений
        // (Picasso, например)
        private Bitmap getBitmapFromUrl(String url) {
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                is.close();
                return bitmap;
            } catch (Exception e) {
                return null;
            }
        }
    }

}
