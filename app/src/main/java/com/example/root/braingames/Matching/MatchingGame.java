package com.example.root.braingames.Matching;

import android.content.Intent;
import android.util.Log;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.root.braingames.Button;
import com.example.root.braingames.GameOver;
import com.example.root.braingames.MainActivity;
import com.example.root.braingames.Matchsticks.Board;
import com.example.root.braingames.Matchsticks.MatchstickGame;
import com.example.root.braingames.R;
import com.example.root.braingames.Timer;

public class MatchingGame extends Activity {
    private MatchingBoard board;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchinggame);

        board = new MatchingBoard(this);
        ((RelativeLayout) findViewById(R.id.matchingboard)).addView(board);


        // generateProblem();
        TextView time = (TextView) findViewById(R.id.timer);
        /*timer = new Timer(time, this);
        timer.start();
        findViewById(R.id.playPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  togglePlayPause(view);
            }
        });
        findViewById(R.id.restart).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
              //  restart();

            }
        });
        */
    }



    public MatchingGame (){}

    public MatchingGame(MainActivity mainActivity){
        mainActivity.setContentView(R.layout.matchinggame);
        RelativeLayout gridLayout = (RelativeLayout) mainActivity.findViewById(R.id.matchingboard);

        Button[][] buttons = new Button[4][4];
        for (int row=0; row<buttons.length; row++){
            for (int col=0; col<buttons[row].length; col++){
                buttons[row][col] = new Button(mainActivity, row, col);
                buttons[row][col].setBackgroundColor(Color.GRAY);
            }
        }
        for (Button[] row : buttons) {
            for (Button button : row)
                gridLayout.addView(button, button.getParams(1.0 / 4,  1/ 4));
        }
    }
    public void gameover(int numcardflip) {
        Intent newGameIntent = new Intent(this, GameOver.class);
        newGameIntent.putExtra("CARDS_FLIPPED", ""+numcardflip);
        newGameIntent.putExtra("FROM_CLASS", "com.example.root.braingames.Matching.MatchingGame");
        Log.i("win status", "Win!" + "" + numcardflip);
        newGameIntent.putExtra("GAME_STATUS", "gameWon");
        startActivity(newGameIntent);
        finish();
    }
}

