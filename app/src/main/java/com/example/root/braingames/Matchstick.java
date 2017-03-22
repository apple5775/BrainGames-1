package com.example.root.braingames;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.widget.GridLayout;

public abstract class Matchstick extends Button {
    private int row, col;
    public Matchstick(MainActivity mainActivity, int row, int col){
        super(mainActivity, row, col);
        this.row = row;
        this.col = col;
    }

    /**
     * @param cellWidthFraction ex) 1/7 should be 1/7
     * @param cellHeightFraction ex) 1/7, 2/3 of the screen should be 2/21
     * @return
     */
    public GridLayout.LayoutParams getParams(double cellWidthFraction, double cellHeightFraction) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                GridLayout.spec(row, GridLayout.CENTER), GridLayout.spec(col, GridLayout.CENTER));

        layoutParams.width = (int)(displayMetrics.widthPixels * cellWidthFraction);
        layoutParams.height = (int)(displayMetrics.heightPixels * cellHeightFraction);
        return layoutParams;
    }
}