package com.example.root.braingames.Matchsticks;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.root.braingames.Button;
import com.example.root.braingames.MainActivity;
import com.example.root.braingames.R;

public class Board extends GridLayout {
    public int[][] clickMap;
    public Button[][] sticks;
    private MatchstickGame matchstickGame;

    public Board(MainActivity mainActivity, MatchstickGame matchstickG) {
        super(mainActivity);
        setColumnCount(7);
        setRowCount(7);

        matchstickGame = matchstickG;
        clickMap = new int[7][7];
        sticks = new Button[7][7];
        boolean isButton = false;
        GridLayout boardLayout = (GridLayout) mainActivity.findViewById(R.id.board);
        OnClickListener boardOnClickListener;

        boardOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean found = false;
                int row=0, col=0;
                if (!matchstickGame.timer.paused) {
                    for (row = 0; row < 7; row++) {
                        for (col = 0; col < 7; col++) {
                            if (sticks[row][col] != null && v == sticks[row][col]) {
                                sticks[row][col].setVisibility(View.INVISIBLE);
                                found = true;
                                break;
                            }
                        }
                        if (found) break;
                    }
                    if (found) {
                        clickMap[row][col] = 0;
                        //matchstickGame.registerClick();    //Welp
                    }
                }
            }

        };

        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                Button button = new Button(mainActivity, row, col);
                if (row % 2 == 0 && col % 2 == 1) {
                    clickMap[row][col] = 2;
                    button.setImageResource(R.drawable.matchstick_horizontal);
                    isButton = true;
                }
                if (row % 2 == 1 && col % 2 == 0) {
                    clickMap[row][col] = 1;
                    button.setImageResource(R.drawable.matchstick_vertical);
                    isButton = true;
                }
                if (isButton == true) {
                    button.setOnClickListener(boardOnClickListener);
                    /*button.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toggleMatchButton(v);
                        }
                    }); */
                    /*button.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Button justClicked = (Button) view;
                            //button.setImageResource(R.drawable.play_button);
                            Log.i("clocked button", "clicked"); //dbg

                            if (!matchstickGame.timer.paused) {
                                //view.setVisibility(view.INVISIBLE);
                                Log.i("Time not paused", "ïnvisible");
                                Log.d("Button Row", "" + justClicked.getRow());
                                Log.d("Button Col", "" + justClicked.getCol());
                                //Button curbut = sticks[justClicked.getRow()][justClicked.getCol()];
                                //justClicked.setVisibility(view.GONE);
                                sticks[4][3].setImageResource(R.drawable.play_button);
                                Log.d("Still here?", "Hmm");
                                //justClicked.setVisibility(justClicked.INVISIBLE);

                            }
                            clickMap[justClicked.getRow()][justClicked.getCol()] = 0;
                            matchstickGame.registerClick();
                        }
                    });*/
                    sticks[row][col] = button;
                    boardLayout.addView(sticks[row][col], sticks[row][col].getParams((1 / 7.0), (2 / 21.0)));

                } else {
                    sticks[row][col] = null;
                }
                isButton = false;
            }
        }
    }

    /* Not used
    public void toggleMatchButton(View view){
        Button justClicked = (Button) view;
        sticks[3][4].setVisibility(View.GONE);
        //button.setImageResource(R.drawable.play_button);
        Log.i("clocked button", "clicked"); //dbg

        if (!matchstickGame.timer.paused){
            //view.setVisibility(view.INVISIBLE);
            Log.i("Time not paused", "ïnvisible");
            Log.d("Button Row", "" + justClicked.getRow());
            Log.d("Button Col", "" + justClicked.getCol());
            //Button curbut = sticks[justClicked.getRow()][justClicked.getCol()];
            //justClicked.setVisibility(view.GONE);
            //sticks[4][3].setVisibility(View.GONE);
            Log.d("Still here?", "Hmm");
            //justClicked.setVisibility(justClicked.INVISIBLE);

            justClicked.setImageResource(R.drawable.play_button);
            clickMap[justClicked.getRow()][justClicked.getCol()] = 0;
            matchstickGame.registerClick();
        }
     }
     */

    public String toString(){
        String rString = "";
        for (int[] row : clickMap)
            for (int el : row)
                rString += Integer.toString(el);
        return rString;
    }
}
