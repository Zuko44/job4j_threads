package ru.job4j.thread;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

import static java.lang.Thread.sleep;

public class Wgett implements Runnable {
    private final String url;
    private final int speed;
    private final Path fileName;

    public Wgett(String url, int speed, Path fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    private static void check(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("there should be 3 parameters");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        check(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Path fileName = Path.of(args[2]);
        Thread wget = new Thread(new Wgett(url, speed, fileName));
        wget.start();
        wget.join();
    }

    @Override
    public void run() {
        try (InputStream in = new URL(url).openStream();
             FileOutputStream fileOutputStream = new FileOutputStream(fileName.toFile())) {
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
