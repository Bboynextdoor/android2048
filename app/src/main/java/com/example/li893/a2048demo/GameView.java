package com.example.li893.a2048demo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;

import java.util.ArrayList;


public class GameView extends GridLayout {
    private Cube cube;
    ArrayList<int[][]> step = new ArrayList<>();
    ArrayList<Integer> gradeStep = new ArrayList<>();


    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    private void initGameView() {
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        cube = new Cube();
        setCard();
        step.add(getCopyCube());
        gradeStep.add(0);
    }


    public void setCard() {
        removeAllViews();
        Card c;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                c = new Card(getContext());
                c.setNum(cube.getEachCube(i, j));
                addView(c);
            }
        }
    }

    public Cube getCube() {
        return cube;
    }


    public void back() {
        if (step.size() > 1) {
            step.remove(step.size() - 1);
            gradeStep.remove(gradeStep.size() - 1);
            cube.setCube(step.get(step.size() - 1));
            cube.setGrade(gradeStep.get(gradeStep.size() - 1));
        }
    }

    public int[][] getCopyCube() {
        int[][] copyCube = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                copyCube[i][j] = cube.getEachCube(i, j);
            }
        }
        return copyCube;
    }

    public void setCube(Cube cube) {
        this.cube = cube;
    }
}




