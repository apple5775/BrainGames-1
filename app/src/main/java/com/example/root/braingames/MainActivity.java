package com.example.root.braingames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MatchingGame mg1 = new MatchingGame(this);
        MatchstickGame mg2 = new MatchstickGame(this);
    }
}
