package com.guozr;

import static com.guozr.StartThread.*;
import static com.guozr.Util.getTime;

public class TeachThread implements Runnable {

    //教师状态
    static TeacherStatus teachStatus[] = new TeacherStatus[M];
    static int teachHaveChecked[] = new int[M];
    private int teachID;

    TeachThread(int teachId) {
        teachID = teachId;
        teachStatus[teachId] = TeacherStatus.Relaxing;
        teachHaveChecked[teachId] = 0;
    }

    @Override
    public void run() {
        System.setOut(ps);
        System.out.println(getTime() + "\t\tTeacher \t" + teachID + "\t enters the classroom.");

        while (true) {
            try {
                Thread.sleep(1);
                if (teachLeave.availablePermits() == 0) {
                    System.setOut(ps);
                    System.out.println(getTime() + " Teacher " + teachID +
                            " CANNOT LEAVE(SOMEONE is trying or judging)");
                }
                teachLeave.acquire();

                if (timeRemain < D) {
                    if (teachStatus[teachID] == TeacherStatus.Reviewing) {
                        break;
                    } else {
                        teachStatus[teachID] = TeacherStatus.Left;
                        System.setOut(ps);
                        System.out.println(getTime() + "\t\tTeacher \t" +
                                teachID + "\t leaves the classroom." +
                                "(No time for another review)");
                        break;
                    }
                }

                if (teachHaveChecked[teachID] == N) {
                    teachStatus[teachID] = TeacherStatus.Left;
                    System.setOut(ps);
                    System.out.println(getTime() + "\t\tTeacher \t" + teachID +
                            "\t leaves the classroom.(Have checked " + N + " projects)");
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            teachLeave.release();

        }

        teachLeave.release();

    }

}
