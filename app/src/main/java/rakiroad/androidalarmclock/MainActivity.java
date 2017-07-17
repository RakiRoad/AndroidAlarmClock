package rakiroad.androidalarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    //Variables for alarm manager

    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView alarm_status;
    Context context;
    PendingIntent pending_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.context = this;

        //initializing alarm manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //initializing time picker
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);

        //initializing status text
        alarm_status = (TextView) findViewById(R.id.alarm_status);

        //Calendar instance
        final Calendar calendar = Calendar.getInstance();

        //creating the intent for Alarm Manager
        final Intent myIntent = new Intent(this.context, Alarm_Receiver.class);


        //inialize alarm buttons
        final Button alarm_on = (Button) findViewById(R.id.alarm_on);
        Button alarm_off = (Button) findViewById(R.id.alarm_off);

        //On Click Listener
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //setting the hour and minute designated by the time picker
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                int hour =  alarm_timepicker.getHour();
                int minute =  alarm_timepicker.getMinute();

                String hourString = String.valueOf(hour);
                String minuteString = String.valueOf(minute);

                if (minute < 10){
                    minuteString = "0" + String.valueOf(minute);
                }

                //Update Text Box when alarm is on
                set_alarm_text("Alarm is active! " + hourString + ":" + minuteString);

                //Create the pending intent until specified time
                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                //setting the alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
            }
        });

        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Update Text Box when alarm is off
                set_alarm_text("Alarm is off!");

                //canceling the alarm
                alarm_manager.cancel(pending_intent);
            }
        });

    }

    private void set_alarm_text(String output) {
        alarm_status.setText(output);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
