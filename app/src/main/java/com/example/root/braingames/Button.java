package com.example.root.braingames;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.widget.GridLayout;
import android.widget.ImageButton;

public class Button extends ImageButton {
    public static double[] MATCHSTICK_PARAMS = {(1/7.0), (2/21.0)};
    private int row, col;

    public Button(MainActivity mainActivity, int row, int col) {
        super(mainActivity);
        this.row = row;
        this.col = col;
        setScaleType(ScaleType.CENTER_CROP);
        setBackgroundColor(0xFFFFFF);
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
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

    public GridLayout.LayoutParams getParams(double[] cellDimens){
        return getParams(cellDimens[0], cellDimens[1]);
    }
}
