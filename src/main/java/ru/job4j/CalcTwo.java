package ru.job4j;

import java.util.Random;

public class CalcTwo {

    private int y;

    public int calc() {
        for (int i = 0; i < 10; i++) {
            y += new Random().nextInt(100) + 12;
        }
        return y;
    }

}
