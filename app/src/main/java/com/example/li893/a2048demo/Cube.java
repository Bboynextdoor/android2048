package com.example.li893.a2048demo;

import java.io.Serializable;

public class Cube implements Serializable {
    private int[][] cube = new int[4][4];
    private int grade = 0;
    private static boolean isMove;
    private static boolean isOver = true;

    public Cube() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cube[i][j] = 0;
            }
        }
        beginning();
    }

    public void beginning() {
        int time = 0;
        double percent;
        double chance;
        percent = 2.0 / 16.0;
        OUT:
        while (time < 2) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    chance = Math.random();
                    if (chance < percent && cube[i][j] == 0) {
                        cube[i][j] = 2;
                        time++;
                    }
                    if (time == 2) {
                        break OUT;
                    }
                }
            }
        }
    }

    public void newOut() {
        int count = 0;
        int time = 0;
        double percent;
        double chance;
        for (int[] k : cube) {
            for (int ks : k) {
                if (ks == 0) {
                    count++;
                }
            }
        }
        percent = 1.0 / count;
        OUT:
        while (time < 1) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    chance = Math.random();
                    if (chance < percent && cube[i][j] == 0) {
                        cube[i][j] = 2;
                        time++;
                    }
                    if (time == 1) {
                        break OUT;
                    }
                }
            }
        }

    }

    public void keepGoing(int a, int b) {
        isOver = true;
        isMove = false;
        if (a == 1 && b == 0 || a == 0 && b == 1) {
            for (int i = 3; i > -1; i--) {
                for (int j = 3; j > -1; j--) {
                    if (j == 3 && b == 1 || i == 3 && a == 1) {
                    } else if (cube[i][j] != 0) {
                        letMove(i, j, a, b);
                    }
                }
            }
        } else {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (j == 0 && b == (-1) || i == 0 && a == (-1)) {
                    } else if (cube[i][j] != 0) {
                        letMove(i, j, a, b);
                    }
                }
            }
        }
    }

    public void letMove(int i, int j, int a, int b) {
        while (i + a < 4 && j + b < 4 && i + a > -1 && j + b > -1) {
            if (cube[i + a][j + b] == 0) {
                cube[i + a][j + b] = cube[i][j];
                cube[i][j] = 0;
                i += a;
                j += b;
                isMove = true;
            } else if (cube[i + a][j + b] == cube[i][j]) {
                cube[i + a][j + b] *= 2;
                grade = grade + cube[i + a][j + b];
                cube[i][j] = 0;
                isMove = true;
                break;
            } else if (cube[i + a][j + b] != cube[i][j]) {
                break;
            }
        }
    }

    public void check() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cube[i][j] == 0) {
                    isOver = false;
                }
            }
        }
        if (isOver) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (cube[i][j] == cube[i + 1][j]) {
                        isOver = false;
                    }
                }
            }
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (cube[i][j] == cube[i][j + 1]) {
                        isOver = false;
                    }
                }
            }
        }
    }

    public void setCube(int[][] cube) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.cube[i][j] =cube[i][j];
            }
        }
    }

    public int getEachCube(int i, int j) {
        return cube[i][j];
    }

    public boolean getIsMove() {
        return isMove;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade=grade;
    }

    public boolean getIsOver() {
        return isOver;
    }

}