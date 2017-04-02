package com.example.root.braingames;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.util.Log;

import com.example.root.braingames.Matching.MatchingGame;
import com.example.root.braingames.Matchsticks.MatchstickGame;

//public class MainActivity extends AppCompatActivity implements StateChange {
  public class MainActivity extends Activity implements StateChange {
    private StateChange inFocus;
    public ImageButton gameButton1, gameButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //super.onCreate(null);
        setContentView(R.layout.activity_main);

        //Intent newMainIntent = getIntent();
        inFocus = this;
        gameButton1 = (ImageButton) findViewById(R.id.msGameButton);
        gameButton1.setImageResource(R.drawable.matchstick_horizontal);
        gameButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Intent button", "clicked");
                Intent gameIntent = new Intent(MainActivity.this, MatchstickGame.class);
                gameIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gameIntent);
                //MatchstickGame mg = new MatchstickGame(MainActivity.this);
                //MainActivity.this.inFocus = mg;
            }
        });

        gameButton2 = (ImageButton) findViewById(R.id.matchingGameButton);
        gameButton2.setImageResource(R.drawable.matchstick_vertical);
        gameButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Intent button", "clicked");
                /*Intent gameIntent = new Intent(MainActivity.this, MatchstickGame.class);
                startActivity(gameIntent);*/
                MatchingGame mg = new MatchingGame(MainActivity.this);
                MainActivity.this.inFocus = mg;
            }
        });
        /*
        MatchingGame mg1 = new MatchingGame(this);
        MatchstickGame mg2 = new MatchstickGame(this);
        inFocus = mg2; */
    }

    @Override
    public void onPause(){
        super.onPause();
        inFocus.pauseActivity();
    }

    @Override
    public void onResume(){
        super.onResume();
        inFocus.resumeActivity();
    }

    public void pauseActivity(){}
    public void resumeActivity(){}

}
