package com.example.root.braingames;

/**
 * Created by kenny on 3/31/17.
 */
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;

public class GameOver extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);

        Intent overIntent = getIntent();
        String winLose = overIntent.getStringExtra("GAME_STATUS");
        Log.i("In Game Over", winLose);
        String text;

        if (winLose.equals("gameWon")) {
            text = "Game Over!" + "\n You won!";
        } else if (winLose.equals("gameLost")){
            text = "Game Over!" + "\n Sorry, you lost.";
        } else {
            text = "Game Over!";
        }
        TextView statusView= (TextView) findViewById(R.id.status);
        statusView.setText(text);

        ImageButton playAgain=(ImageButton)findViewById(R.id.playAgainButton);
        playAgain.setImageResource(R.drawable.play_button);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent (GameOver.this, MainActivity.class);
                playIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(playIntent);
                finish();
            }
        });
    }
}
