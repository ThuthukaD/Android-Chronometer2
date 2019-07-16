package com.example.chronometer2;

import android.content.Context;
import android.support.annotation.UiThread;

public class Chronometer implements Runnable
{
    // VARIABLES
    private Context context;
    private long startTime;
    private boolean isRunning;

    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLIS_TO_HOURS = 3600000;

    public Chronometer(Context context)
    {
        this.context = context;
    }

    public void start()
    {
        // Will get the system time from android
        startTime = System.currentTimeMillis();

        isRunning = true;
    }

    public void stop()
    {
        isRunning = false;
    }

    @Override
    public void run()
    {
        while (isRunning)
        {
            // how many millis have passed "since started"?
            long sinceStarted = System.currentTimeMillis() - startTime;

            int seconds = (int) (sinceStarted / 1000) % 60;
            int minutes = (int) (sinceStarted / MILLIS_TO_MINUTES) % 60;
            int hours = (int) (sinceStarted / MILLIS_TO_HOURS % 24);
            int millis = (int) sinceStarted % 1000;

            ((MainActivity)context).updateTimerText(String.format(
                    "%03d:%02d:%02d:%03d", hours, minutes, seconds, millis
            ));
        }
    }
}