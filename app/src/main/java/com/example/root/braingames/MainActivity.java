package com.example.root.braingames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements StateChange {
    private StateChange inFocus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inFocus = this;
//        MatchingGame mg1 = new MatchingGame(this);
        MatchstickGame mg2 = new MatchstickGame(this);
        inFocus = mg2;
    }

    public void onPause(){
        super.onPause();
        inFocus.pauseActivity();
    }

    public void onResume(){
        super.onResume();
        inFocus.resumeActivity();
    }

    public void pauseActivity(){}
    public void resumeActivity(){}
}
