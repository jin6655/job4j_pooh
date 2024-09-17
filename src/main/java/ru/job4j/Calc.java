package ru.job4j;

import java.util.Random;

public class Calc {
    
    private int x;
    
    public int calc() {
        for (int i = 0; i < 10; i++) {
            x += new Random().nextInt(100);
        }
        return  x;
    }
    
}
