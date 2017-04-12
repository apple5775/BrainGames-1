package com.example.root.braingames.Matching;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;

import com.example.root.braingames.Button;
import com.example.root.braingames.GameOver;
import com.example.root.braingames.Matching.MatchingGame;
import com.example.root.braingames.Matchsticks.MatchstickGame;
import com.example.root.braingames.R;
import android.os.Handler;

/**
 * Created by kenny on 4/9/17.
 */



public class MatchingBoard extends GridLayout {
    public int[][] imageMap;
    public int maxClick;
    public boolean evenclick;
    public boolean waiting;
    public Button prevButton;
    public Button[][] sticks;
    public int [][] flipMap;
    private MatchingGame matchingGame;
    public int boardwidth = 4;
    public int boardheight = 4;
    public int pairsneeded = boardheight * boardwidth / 2;
    public int cardsflipped;

    private Handler mhandler = new Handler();

    public MatchingBoard(MatchingGame matchingG) {
        super(matchingG);
        setColumnCount(boardwidth);
        setRowCount(boardheight);
        evenclick = true;
        waiting = false;
        matchingGame = matchingG;
        imageMap = makeDefaultBoard(boardheight, boardwidth);
        sticks = new Button[boardheight][boardwidth];
        flipMap = new int[boardheight][boardwidth];
        for (int k = 0; k < boardheight; k++)
            for (int p = 0; p < boardwidth; p++)
                flipMap[k][p] = 0;

        OnClickListener boardOnClickListener;


        boardOnClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Board", "clicked");
              /* if (!matchingGame.timer.paused) {
                    Log.d("Board", "timer not paused"); */
                if (v != null) {
                    Log.d("Board", "before setting invisible");
                    processclick((Button) v);
                } else {
                    Log.d("Board", "view is null");
                }
                //}
            }
        };



        for (int row = 0; row < boardheight; row++) {
            for (int col = 0; col < boardwidth; col++) {
                Button button = new Button(matchingGame, row, col);
                button.setImageResource(R.drawable.moving_questionmark);
                button.setOnClickListener(boardOnClickListener);
                sticks[row][col] = button;
                addView(sticks[row][col], sticks[row][col].getParams((1.0/boardheight), (1.0/boardwidth*3/5)));
            }
        }
    }



    private boolean imageChange(Button view){
        int row = view.getRow();
        int col = view.getCol();
        Log.i("ïn imageChange", ""+row + ","+col);
        switch (imageMap [row][col]) {
            case 0:
                view.setImageResource(R.drawable.pet_camel);
                break;
            case 1:
                view.setImageResource(R.drawable.pet_cat);
                break;
            case 2:
                view.setImageResource(R.drawable.pet_dog_ball);
                break;
            case 3:
                view.setImageResource(R.drawable.pet_elephant);
                break;
            case 4:
                view.setImageResource(R.drawable.pet_fish);
                break;
            case 5:
                view.setImageResource(R.drawable.pet_monkey_nightmare);
                break;
            case 6:
                view.setImageResource(R.drawable.pet_mouse);
                break;
            case 7:
                view.setImageResource(R.drawable.pet_parrot);
                break;
            default:
                view.setImageResource(R.drawable.fox_mascot);
                return false;
        }
        return true;
    }


    public static int[][] makeDefaultBoard(int rownum, int colnum){
        int[][] defaultBoard = new int[rownum][colnum];
        int temp;
        int randrow, randcol;
        for (int r=0; r<rownum; r++) {
            for (int c = 0; c < colnum; c++) {
                defaultBoard[r][c] = (r * colnum + c) % (rownum * colnum / 2);

            }
        }

        for (int r=0; r<rownum; r++) {
            for (int c = 0; c < colnum; c++) {
                randrow = (int)(Math.random() * (rownum));
                randcol = (int)(Math.random() * (colnum));
                temp = defaultBoard[r][c];
                Log.d("swap", "row" + r + "col" + c + "Image:" + defaultBoard[r][c]+ "with " + "randrow"
                        +randrow+"randcol " + randcol + "IMage" + defaultBoard[randrow][randcol]);
                defaultBoard[r][c] = defaultBoard[randrow][randcol];
                defaultBoard[randrow][randcol] = temp;
            }
        }
        return defaultBoard;
    }

    public boolean processclick(final Button view){
        if ((flipMap[view.getRow()][view.getCol()] == 0) && (waiting == false)) {
            imageChange(view);
            evenclick = !evenclick;
            waiting = true;
            if (evenclick == true)  {
                if (imageMap[prevButton.getRow()][prevButton.getCol()] == imageMap[view.getRow()][view.getCol()]) {
                    flipMap[view.getRow()][view.getCol()] = 2;
                    flipMap[prevButton.getRow()][prevButton.getCol()] = 2;
                    pairsneeded = pairsneeded - 1;
                    waiting = false;
                    if (pairsneeded == 0){
                        cardsflipped = cardsflipped + 1;
                        matchingGame.gameover(cardsflipped);
                    }
                } else {
                    mhandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageResource(R.drawable.moving_questionmark);
                            prevButton.setImageResource(R.drawable.moving_questionmark);
                            // Log.i("hello", ""+waiting);
                            waiting = false;
                        }
                    }, 2000);

                    flipMap[prevButton.getRow()][prevButton.getCol()] = 0;
                    flipMap[view.getRow()][view.getCol()] = 0;

                }
            } else {
                prevButton = view;
                flipMap[view.getRow()][view.getCol()] = 1;
                waiting = false;
            }
            cardsflipped = cardsflipped + 1;
        }

        return true;
    }

    private void doStuff() {
        Log.i("Hello", "Can you wait?");
    }
    public static String arrayToString(int[][] imageMap){
        String cms = "";
        for (int[] row : imageMap)
            for (int el : row)
                cms += Integer.toString(el);
        return cms;
    }

    public static int[][] stringToArray(String cms){
        int[][] imageMap = new int[7][7];
        for (int row=0; row<7; row++)
            for (int col=0; col<7; col++)
                imageMap[row][col] = Integer.parseInt(cms.substring((row*7)+col, (row*7)+col+1));
        return imageMap;
    }
}
