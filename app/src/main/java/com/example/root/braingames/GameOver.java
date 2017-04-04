package com.example.root.braingames;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

import com.example.root.braingames.Matchsticks.Board;

public class GameOver extends Activity {
    private Class<?> from;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);

        Intent overIntent = getIntent();
        String winLose = overIntent.getStringExtra("GAME_STATUS");
        String winningMap = overIntent.getStringExtra("WINNING_MAP");

        from = null;
        try {
            from = Class.forName(overIntent.getStringExtra("FROM_CLASS"));
        } catch (ClassNotFoundException e) {
            Log.i("Error", "Didn't find from class");
        }

        Log.i("In Game Over", winLose);
        String text;

        if (winLose.equals("gameWon")) {
            text = "Game Over!" + "\n You won!";
        } else if (winLose.equals("gameLost")) {
            text = "Game Over!" + "\n Sorry, you lost.";
        } else {
            text = "Game Over!";
        }
        TextView statusView = (TextView) findViewById(R.id.status);
        statusView.setText(text);

        if (winningMap != ""){
            Board board = new Board(this, winningMap);
            ((LinearLayout)findViewById(R.id.solution)).addView(board);
        }

        ImageButton menu = (ImageButton) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(GameOver.this, MainActivity.class);
                playIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(playIntent);
                finish();
            }
        });

        ImageButton restart = (ImageButton) findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent restartPast = new Intent(GameOver.this, from);
                restartPast.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(restartPast);
                finish();
            }
        });
    }
}
