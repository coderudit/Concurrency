package com.concurrency.app;

import static com.concurrency.app2.ThreadColor.ANSI_BLUE;

public class AnotherThread extends Thread{

    @Override
     public void run(){
         System.out.println(ANSI_BLUE + "Hello from another thread.");
    try{
        Thread.sleep(5000);
    } catch (InterruptedException e){
        System.out.println(ANSI_BLUE + "Another thread woke me up.");
        return;
    }
    System.out.println(ANSI_BLUE + "Three seconds have passed and I am awake.");
    }
}
