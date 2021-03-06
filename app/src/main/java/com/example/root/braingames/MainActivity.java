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

public class MainActivity extends Activity {
    public ImageButton gameButton1, gameButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameButton1 = (ImageButton) findViewById(R.id.msGameButton);
        gameButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Intent button", "clicked");
                Intent gameIntent = new Intent(MainActivity.this, MatchstickGame.class);
                gameIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gameIntent);
                finish();
            }
        });

        gameButton2 = (ImageButton) findViewById(R.id.matchingGameButton);
        gameButton2.setImageResource(R.drawable.matchstick_vertical);
        gameButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Intent button", "clicked");
                Intent matching = new Intent(MainActivity.this, MatchingGame.class);
                matching.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(matching);
                finish();
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
