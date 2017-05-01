package com.ice.testgoodidea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ice.testgoodidea.view.WaveButton;

/**
 * Created by asd on 5/1/2017.
 */

public class WaveButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wave_activity);

        final WaveButton waveButton = (WaveButton) findViewById(R.id.wave_button);
        waveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveButton.startAnimation();
            }
        });

    }
}
