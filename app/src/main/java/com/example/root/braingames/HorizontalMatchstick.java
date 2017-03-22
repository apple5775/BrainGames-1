package com.example.root.braingames;

public class HorizontalMatchstick extends Matchstick {
    private int row, col;
    public HorizontalMatchstick(MainActivity mainActivity, int row, int col){
        super(mainActivity, row, col);
        super.setImageResource(R.drawable.matchstick_horizontal);
    }
}
