package com.concurrency.synchronization_2;

public class Main {
    public static void main(String[] args) {
        //Multiple threads with single countdown object.
        /*Countdown countdown = new Countdown();

        Thread thread1 = new CountdownThread(countdown);
        thread1.setName("Thread 1");

        Thread thread2 = new CountdownThread(countdown);
        thread2.setName("Thread 2");

        thread1.start();
        thread2.start();*/

        //Multiple threads with multiple countdown objects, this does not affect the threads.
        /*Countdown countdown1 = new Countdown();
        Countdown countdown2 = new Countdown();

        Thread thread1 = new CountdownThread(countdown1);
        thread1.setName("Thread 1");

        Thread thread2 = new CountdownThread(countdown2);
        thread2.setName("Thread 2");

        thread1.start();
        thread2.start();*/

        //Multiple threads with single countdown object.
        Countdown countdown = new Countdown();

        Thread thread1 = new CountdownThread(countdown);
        thread1.setName("Thread 1");

        Thread thread2 = new CountdownThread(countdown);
        thread2.setName("Thread 2");

        thread1.start();
        thread2.start();

    }
}

class Countdown {
    //Prone to changes by both threads at the same time.
    //One thread can't access the thread stack of another thread.
    //But it can access the heap which is common.
    private int index;

    //Without synchronization multiple threads can execute them and thus, causing a race condition.
    /*public void doCountDown() {
        String color;

        switch (Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColor.ANSI_CYAN;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_PURPLE;
                break;
            default:
                color = ThreadColor.ANSI_GREEN;
                break;
        }

        //This won't be affected in case of multiple threads being a local variable.
        /*for(int index = 10; index > 0; index--){
            System.out.println(color + Thread.currentThread().getName() + ": index = " + index);
        }*/

        //This gets affected being an instance variable.
        /*for (index = 10; index > 0; index--) {
            System.out.println(color + Thread.currentThread().getName() + ": index = " + index);
        }
    }*/

    //With synchronized method, multiple threads can run them with 1 at a time without interference.
    /*public synchronized void doCountDown() {
        String color;

        switch (Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColor.ANSI_CYAN;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_PURPLE;
                break;
            default:
                color = ThreadColor.ANSI_GREEN;
                break;
        }

        //This won't be affected in case of multiple threads being a local variable.
        /*for(int index = 10; index > 0; index--){
            System.out.println(color + Thread.currentThread().getName() + ": index = " + index);
        }*/

        //This gets affected being an instance variable.
        /*for (index = 10; index > 0; index--) {
            System.out.println(color + Thread.currentThread().getName() + ": index = " + index);
        }
    }*/

    //With synchronized statements, multiple threads can run them with 1 at a time without interference.
    public void doCountDown() {
        String color;

        switch (Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColor.ANSI_CYAN;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_PURPLE;
                break;
            default:
                color = ThreadColor.ANSI_GREEN;
                break;
        }

        //This won't be affected in case of multiple threads being a local variable.
        /*for(int index = 10; index > 0; index--){
            System.out.println(color + Thread.currentThread().getName() + ": index = " + index);
        }*/

        //This gets affected being an instance variable.
        //synchronized(color) { //Local variables is synchronized here, this will act same as without lock.
        synchronized(this) {
            for (index = 10; index > 0; index--) {
                System.out.println(color + Thread.currentThread().getName() + ": index = " + index);
            }
        }
        //}
    }
}

class CountdownThread extends Thread {
    private Countdown threadCountDown;

    public CountdownThread(Countdown countdown) {
        this.threadCountDown = countdown;
    }

    public void run() {
        threadCountDown.doCountDown();
    }
}
