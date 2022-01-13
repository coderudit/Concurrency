package com.concurrency.messages_3;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Message message = new Message();
        (new Thread(new Writer(message))).start();
        (new Thread(new Reader(message))).start();
    }
}

class Message {
    private String message;
    private boolean empty = true;

    //Used by the consumer to read the message.
    public synchronized String read() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        empty = true;
        notifyAll();
        return message;
    }

    //Used by the producer to write the message.
    public synchronized void write(String message) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        empty = false;
        this.message = message;
        notifyAll();
    }
}

//Producer class
class Writer implements Runnable {
    private Message message;

    public Writer(Message message) {
        this.message = message;
    }


    @Override
    public void run() {
        String messages[] = {"Message 1", "Message 2", "Message 3", "Message 4"};

        Random random = new Random();
        for (int index = 0; index < messages.length; index++) {
            message.write(messages[index]);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }

        message.write("Finish");
    }
}

//Producer class
class Reader implements Runnable {
    private Message message;

    public Reader(Message message) {
        this.message = message;
    }


    @Override
    public void run() {
        Random random = new Random();
        for (String latestMessage = message.read(); !latestMessage.equals("Finished"); ) {
            latestMessage = message.read();
            System.out.println(latestMessage);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
