package rakiroad.androidalarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import java.security.Provider;

public class RingtonePlayingService extends Service{

    MediaPlayer media_song;
    boolean isRunning;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("ring", "tone");

        String state = intent.getExtras().getString("extra");

        Log.e("Ringtone state: ", state );



        //sets the startId depending on state
        assert state !=null;
        switch (state) {
            case "alarm 1":
                startId = 1;
                break;
            case "alarm 0":
                Log.e("Is it off? ", state );
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }


        //if else for playing alarm sound

        //if alarm is not running and alarm on is pressed music will play
        if (!this.isRunning && startId == 1){

            Log.e("music none", "start");
            media_song = MediaPlayer.create(this, R.raw.rooster2);
            media_song.start();

            this.isRunning = true;
            startId = 0;
            //Notication set up
            NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            //sets up the intent
            Intent intentMainActivity = new Intent (this.getApplicationContext(), MainActivity.class);

            PendingIntent pendingintentMainActivity = PendingIntent.getActivity(this, 0, intentMainActivity, 0);

            //create the notification
            Notification notificationPopup = new Notification.Builder(this)
                    .setContentTitle("ALARM IS RINGING")
                    .setContentText("Click ME!")
                    .setContentIntent(pendingintentMainActivity)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setAutoCancel(true)
                    .build();

            //setting the notification popup
            notifyManager.notify(0, notificationPopup);
        }

        //if alarm is running and alarm off button is pressed
        else if (this.isRunning && startId == 0){

            Log.e("music on", "stop");

            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            startId = 0;
        }

        //if alarm is not runnign and alarm off is pressed
        else if(!this.isRunning && startId == 0){
            Log.e("music none", "stop");
            this.isRunning = false;
            startId = 0;

        }

        //if alarm on and alarm on is pressed
        else if(this.isRunning && startId == 1){
            Log.e("music on", "start");
            this.isRunning = true;
            startId = 1;

        }
        else{

        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

       Log.e("quit ", "app");
        this.isRunning = false;
    }
}
