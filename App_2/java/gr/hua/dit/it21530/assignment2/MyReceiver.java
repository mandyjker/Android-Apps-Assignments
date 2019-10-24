package gr.hua.dit.it21530.assignment2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent();
        i.setClassName(context,MyService.class.getName());
        //if MyService has not started, then start it. Otherwise, stop it
        if (MyService.hasStarted) {
            context.stopService(i);
        } else {
            context.startService(i);
        }
    }

}
