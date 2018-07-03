/*
 * Copyright (c) 2018, Guo ZR. All rights reserved
 */

package com.guozr;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


class StartThread {

    //    static int S = 5, K = 2, M = 3, N = 4, T = 40, D = 5;
    static int S, K, M, N, T, D;                            //初始变量
    static int timeRemain, timeTotal;
    static String pathName = "output.txt";

    static PrintStream ps;
    //信号量。由于synchronized不是公平锁，所以不采用
    static Semaphore stuLeave = new Semaphore(1);       //每次只有一个学生离开
    static Semaphore stuGrab = new Semaphore(1);        //每次选取一个老师
    static Semaphore teachLeave = new Semaphore(1);     //每次只有一个老师离开
    private static PrintStream old = new PrintStream(System.out);

    static {
        try {
            ps = new PrintStream(pathName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void startThread() {

        timeRemain = timeTotal = T;
        System.setOut(ps);

        //进程池
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new TimeThread());
        for (int i = 0; i < M; i++) {
            exec.execute(new TeachThread(i));
        }
        for (int i = 1; i <= S; i++) {
            exec.execute(new StuThread(i));
        }
        exec.shutdown();

        System.out.flush();
        System.setOut(old);

    }

}
