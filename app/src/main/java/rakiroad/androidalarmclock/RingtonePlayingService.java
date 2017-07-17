package rakiroad.androidalarmclock;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.security.Provider;

/**
 * Created by Rocky on 7/17/2017.
 */

public class RingtonePlayingService extends Service{

    MediaPlayer media_song;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("ring", "tone");
        media_song = MediaPlayer.create(this, R.raw.rooster);
        media_song.start();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "fear", Toast.LENGTH_SHORT).show();
    }
}
