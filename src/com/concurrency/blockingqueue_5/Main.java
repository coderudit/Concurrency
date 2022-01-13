package com.concurrency.blockingqueue_5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<String>(6);

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_CYAN);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_BLUE);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE);

        /*new Thread(producer).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();*/

        executorService.execute(producer);
        executorService.execute(consumer1);
        executorService.execute(consumer2);

        Future<String> future = executorService.submit(() -> {
            System.out.println(ThreadColor.ANSI_PURPLE + "Called from callable section.");
            return "This is callable result.";
        });

        try {
            System.out.println(future.get());
        } catch (ExecutionException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        executorService.shutdown();
    }
}

class MyProducer implements Runnable {
    private ArrayBlockingQueue<String> buffer;
    private String color;

    public MyProducer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for (String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);
                buffer.put(num);
                Thread.sleep((random.nextInt(1000)));
            } catch (InterruptedException ex) {
                System.out.println("Producer was interrupted.");
            }
        }

        System.out.println(color + "Adding EOF and exiting...");
        try {
            buffer.put("EOF");
        } catch (InterruptedException ex) {
            System.out.println("Producer was interrupted.");
        }
        //}
    }
}

class MyConsumer implements Runnable {
    private ArrayBlockingQueue<String> buffer;
    private String color;

    public MyConsumer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        while (true) {
            synchronized(buffer) {
                try {
                    if (buffer.isEmpty()) {
                        continue;
                    }
                    if (buffer.peek().equals(Main.EOF)) {
                        System.out.println(color + "Exiting...");
                        break;
                    } else {
                        System.out.println(color + "Removed " + buffer.take());
                    }
                } catch (InterruptedException ex) {
                    System.out.println("Consumer was interrupted.");
                }
            }
        }
    }
}