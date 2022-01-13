package com.concurrency.threads_1;

import static com.concurrency.synchronization_2.ThreadColor.ANSI_RED;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(ANSI_RED + "Hello from my runnable.");
    }
}
