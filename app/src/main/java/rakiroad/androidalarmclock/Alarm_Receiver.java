package rakiroad.androidalarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Rocky on 7/17/2017.
 */

public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("We are in", "Other");

        //fetching extra string from the intent
        String extraString = intent.getExtras().getString("extra");
        Log.e("String is ", extraString);

        //create intent to rington
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        service_intent.putExtra("extra", extraString);

        //start service
        context.startService(service_intent);
    }

}
