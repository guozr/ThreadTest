package com.guozr;

import java.io.*;
import java.util.Scanner;

import static com.guozr.StartThread.*;

class Util {

    static void getParameters() {
        Scanner sc = new Scanner(System.in);
        boolean flag;
        do {
            System.out.println("请输入学生个数S:");
            S = sc.nextInt();
            System.out.println("请输入老师个数M:");
            M = sc.nextInt();
            System.out.println("请输入检查每个项目需要的老师数K:");
            K = sc.nextInt();
            System.out.println("请输入每个老师可以检查的项目数N:");
            N = sc.nextInt();
            System.out.println("请输入总时间T:");
            T = sc.nextInt();
            System.out.println("请输入检查每个项目所需时间D:");
            D = sc.nextInt();
            flag = S * K > M * N || S < 0 || K < 0 || M < 0 || N < 0;
            if (flag)
                System.out.println("输入不合法！请重新输入！");
        } while (flag);
    }

    static int getTime() {
        return timeTotal - timeRemain;
    }

    static String getText(String pathName) {
        File file = new File(pathName);
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String temp;
            while ((temp = reader.readLine()) != null)
                text.append(temp).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        clearText(pathName);
        return text.toString();
    }

    static void clearText(String pathname) {
        File file = new File(pathname);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
