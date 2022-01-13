package com.concurrency.app;

import static com.concurrency.app.ThreadColor.*;

public class Main {

    public static void main(String[] args) {
        System.out.println(ANSI_PURPLE + "Hello from the main thread.");

        Thread anotherThread = new AnotherThread();
        anotherThread.setName("anotherThread");
        anotherThread.start();

        //Start can be called only once.
        //anotherThread.start();

        //Anonymous thread
        new Thread() {
            public void run() {
                System.out.println(ANSI_GREEN + "Hello again from the anonymous thread.");
            }
        }.start();

        //Runnable thread
        Thread myRunnableThread = new Thread(new MyRunnable());
        myRunnableThread.start();

        //Interrupts another thread in between the sleep
        anotherThread.interrupt();

        //Runnable thread 2 with anonymous
        Thread myRunnableThread2 = new Thread(new MyRunnable() {
            @Override
            public void run() {
                System.out.println(ANSI_BLUE + "Hello again from runnable thread 2.");
            }
        });

        myRunnableThread2.start();

        System.out.println("Hello again from the main thread.");
    }
}
