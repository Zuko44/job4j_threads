package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    /**try {
                        System.out.println("Start loading ... ");
                        Thread.sleep(3000);
                        System.out.println("Loaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    try {
                        System.out.println("Start loading ... ");
                        for (int i = 1; i <= 100; i++) {
                            System.out.print("\rLoading : " + i  + "%");
                            Thread.sleep(200);
                        }
                        System.out.println();
                        System.out.println("Loaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
        System.out.println("Main");
    }
}
