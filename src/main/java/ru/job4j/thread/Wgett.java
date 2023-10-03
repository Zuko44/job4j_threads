package ru.job4j.thread;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static java.lang.Thread.sleep;

public class Wgett implements Runnable {
    private final String url;
    private final int speed;

    public Wgett(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    private static void check(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("there should be 2 parameters");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wgett(url, speed));
        wget.start();
        wget.join();
    }

    @Override
    public void run() {
        try (InputStream in = new URL(url).openStream();
             FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\user\\Downloads\\pom.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long start = System.currentTimeMillis();
            int downloadData = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long operationTime = System.currentTimeMillis() - start;
                    if (operationTime < 1000) {
                        sleep(1000 - operationTime);
                    }
                    downloadData = 0;
                    start = System.currentTimeMillis();
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
