package com.example.root.braingames;

import android.graphics.Color;
import android.widget.GridLayout;

public class MatchingGame implements StateChange {
    public MatchingGame(MainActivity mainActivity){
        mainActivity.setContentView(R.layout.matchinggame);
        GridLayout gridLayout = (GridLayout) mainActivity.findViewById(R.id.board);

        Button[][] buttons = new Button[4][4];
        for (int row=0; row<buttons.length; row++){
            for (int col=0; col<buttons[row].length; col++){
                buttons[row][col] = new Button(mainActivity, row, col);
                buttons[row][col].setBackgroundColor(Color.GRAY);
            }
        }

        for (Button[] row : buttons)
            for (Button button : row)
                gridLayout.addView(button, button.getParams(1/4, 1/4));
    }

    public void pauseActivity(){}
    public void resumeActivity(){}
}
