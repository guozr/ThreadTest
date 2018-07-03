package com.guozr;

import static com.guozr.StartThread.*;
import static com.guozr.TeachThread.teachHaveChecked;
import static com.guozr.TeachThread.teachStatus;
import static com.guozr.Util.getTime;

public class StuThread implements Runnable {

    private int stuID;

    StuThread(int id) {
        stuID = id;
    }

    @Override
    public void run() {

        int teachNeed = K;                      //需要的老师数
        int teachHave = 0;                      //已找到的老师数
        int teachGrabbed[] = new int[K];        //已选择的老师数组
        int num = 0;

        try {
            Thread.sleep((long) ((Math.random() * T) % (T - D)));
            System.setOut(ps);
            System.out.println(getTime() + "\t\tStudent \t" + stuID + "\t enters the classroom.");


            if (stuLeave.availablePermits() == 0) {
                System.setOut(ps);
                System.out.println(getTime() + " Student " + stuID + " CANNOT LEAVE.(SOMEONE is trying or judging)");
            }
            stuLeave.acquire();

            if (timeRemain >= D) {
                System.setOut(ps);
                System.out.println(getTime() + "\t\tStudent \t" + stuID + "\t starts to look for teachers.");

                int index = (int) (Math.random() * 100) % M;        //随机选择老师
                LabelStu:
                {
                    while (true) {
                        if (timeRemain < D) {
                            stuLeave.release();

                            System.setOut(ps);
                            System.out.println(getTime() + "\t\tStudent \t" + stuID +
                                    "\t leaves the classroom.(No time for another review)");
                            break LabelStu;
                        }

                        if (teachStatus[index] == TeacherStatus.Relaxing &&
                                teachHaveChecked[index] < N) {
                            teachStatus[index] = TeacherStatus.BeingGrabbed;
                            teachGrabbed[num] = index;

                            System.setOut(ps);
                            System.out.println(getTime() + "\t\tTeacher \t" + index +
                                    "\t has been grabbed by student " + stuID + ".(Job " +
                                    (teachHaveChecked[index] + 1) + ")");

                            teachHave += 1;
                            num += 1;
                            if (teachHave == teachNeed)
                                break;
                        }
                        index += (int) (Math.random() * 100) % M;
                        index = index % M;
                    }

                    System.setOut(ps);
                    System.out.println(getTime() + "\t\tStudent \t" + stuID + "\t starts review.");

                    stuLeave.release();
                    Thread.sleep(2 * D - 1);

                    stuGrab.acquire();
                    for (int i = 0; i < K; i++) {
                        teachStatus[teachGrabbed[i]] = TeacherStatus.Relaxing;
                        teachHaveChecked[teachGrabbed[i]] += 1;
                    }

                    System.setOut(ps);
                    System.out.println(getTime() + "\t\tStudent \t" + stuID + "" +
                            "\t leaves the classroom.(Have finished his/her review)");
                    stuGrab.release();

                }
            } else {
                System.setOut(ps);
                System.out.println(getTime() + "\t\tStudent \t" + stuID +
                        "\t leaves the classroom.(No time for another review)");
                stuLeave.release();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
