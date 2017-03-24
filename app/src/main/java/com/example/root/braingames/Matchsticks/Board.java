package com.example.root.braingames.Matchsticks;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.example.root.braingames.Button;
import com.example.root.braingames.MainActivity;
import com.example.root.braingames.R;

public class Board extends GridLayout {
    public int[][] clickMap;
    public Button[][] sticks;
    private MatchstickGame matchstickGame;

    public Board(MainActivity mainActivity, MatchstickGame matchstickG){
        super(mainActivity);
        setColumnCount(7);
        setRowCount(7);

        matchstickGame = matchstickG;
        clickMap = new int[7][7];
        sticks = new Button[7][7];

        for (int row=0; row<7; row++){
            for (int col=0; col<7; col++){
                Button button = new Button(mainActivity, row, col);
                if (row % 2 == 0 && col % 2 == 1) {
                    clickMap[row][col] = 2;
                    button.setImageResource(R.drawable.matchstick_horizontal);
                }
                if (row % 2 == 1 && col % 2 == 0) {
                    clickMap[row][col] = 1;
                    button.setImageResource(R.drawable.matchstick_vertical);
                }
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button justClicked = (Button) view;
                        if (!matchstickGame.timer.paused)
                            justClicked.setVisibility(View.INVISIBLE);
                        clickMap[justClicked.getRow()][justClicked.getCol()] = 0;
                        matchstickGame.registerClick();
                    }
                });
                sticks[row][col] = button;
                addView(sticks[row][col], sticks[row][col].getParams((1/7.0), (2/21.0)));
            }
        }
    }

    public String toString(){
        String rString = "";
        for (int[] row : clickMap)
            for (int el : row)
                rString += Integer.toString(el);
        return rString;
    }
}
