package com.concurrency.deadlock_6;

public class Main {
    public static Object lock1 = new Object();
    public static Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread1().start();
        new Thread2().start();
    }

    private static class Thread1 extends Thread {
        public void run() {
            synchronized (lock1) {
                System.out.println("Thread 1: Has Lock 1.");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }

                System.out.println("Thread 1: Waiting for Lock 2.");

                synchronized (lock2) {
                    System.out.println("Thread 1: Has Lock 1 and Lock 2.");
                }

                System.out.println("Thread 1: Released Lock 2.");
            }
            System.out.println("Thread 1: Released Lock 1.");
        }
    }

    private static class Thread2 extends Thread {
        public void run() {
            synchronized (lock2) {
                System.out.println("Thread 2: Has Lock 2.");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }

                System.out.println("Thread 2: Waiting for Lock 1.");

                synchronized (lock1) {
                    System.out.println("Thread 2: Has Lock 1 and Lock 2.");
                }

                System.out.println("Thread 2: Released Lock 1.");
            }
            System.out.println("Thread 2: Released Lock 2.");
        }
    }
}


