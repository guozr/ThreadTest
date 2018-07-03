package com.guozr;


import static com.guozr.StartThread.ps;
import static com.guozr.StartThread.timeRemain;
import static com.guozr.Util.getTime;

public class TimeThread implements Runnable {

    @Override
    public void run() {

        do {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeRemain--;
        } while (timeRemain > 0);

        System.setOut(ps);
        System.out.println(getTime() + "\t\tClass is over");

    }
}
