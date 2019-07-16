package com.example.chronometer2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    // VARIABLES
    private TextView tvTime;
    public TextView tvLaps;
    private Button btnStart;
    private Button btnLap;
    private Button btnStop;
    private Chronometer chronometer;
    private Thread threadChrono;
    private Context context;
    private ScrollView svLaps;
    private int laps = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // INITIALISING
        tvTime = findViewById(R.id.tvTime);
        svLaps = findViewById(R.id.svLaps);
        tvLaps = findViewById(R.id.tvLaps);
        btnStart = findViewById(R.id.btnStart);
        btnLap = findViewById(R.id.btnLap);
        btnStop = findViewById(R.id.btnStop);
        context = this;

//        tvLaps.setEnabled(false);

        // WORK
        startTime();
        stopTime();
        laps();
    }

    public void startTime()
    {
        btnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (chronometer == null)
                {
                    chronometer = new Chronometer(context);
                    threadChrono = new Thread(chronometer);
                    threadChrono.start();
                    chronometer.start();

                    laps = 1;
                    tvTime.setText("");
                }
            }
        });
    }

    public void stopTime()
    {
        btnStop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (chronometer != null)
                {
                    chronometer.stop();
                    threadChrono.interrupt();
                    threadChrono = null;

                    // Restarts the time
                    chronometer = null;
                }
            }
        });
    }

    public void laps()
    {
        btnLap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tvLaps.append("LAP " + laps + " : " +
                        tvTime.getText().toString() + "\n");
                laps++;

                if (chronometer == null)
                {
                    return; // do nothing
                }

                svLaps.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        svLaps.smoothScrollTo(0, tvLaps.getBottom());
                    }
                });
            }
        });
    }

    public void updateTimerText(final String time)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                tvTime.setText(time);
            }
        });
    }
}
