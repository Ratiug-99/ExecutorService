package com.example.ratiug.executorserviceproject;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutorService {

    public static int mCount = 0;
    public static int floor = 100;

    public static void main(String[] args) {   //todo "Run "ExecutorService" with Coverage
        testExecutor();
    }
  

    private static void testExecutor()  {
        int threads = 10;
        java.util.concurrent.ExecutorService executorService = Executors.newFixedThreadPool(threads);
        System.out.println("Запуск потоков");
        for(int i = 0; i < threads; i++){
            executorService.submit(new startEx());
        }
        executorService.shutdown();
    }

    public static void increment(int lCount)  {
        ReentrantLock lock = new ReentrantLock();
        System.out.println("\n--------------------------------------------\nLOCKING THE TOTAL COUNTER");
        lock.lock();
        try {
            ExecutorService.mCount= mCount + lCount;
        } finally {
            System.out.println("Total Counter = " + ExecutorService.mCount);
            System.out.println("UNLOCKING THE TOTAL COUNTER\n--------------------------------------------\n");
            lock.unlock();
        }
    }

}

class startEx  implements Callable<Integer>
{
    int localCount = 0;

    public Integer call() throws Exception
    {
        for (int i = 0; i < ExecutorService.floor; i++){

            int rnd = ( 10 + (int) (Math.random() * 100));
            TimeUnit.MILLISECONDS.sleep(rnd);
            //System.out.println(rnd);
            localCount++;
            System.out.println("Local Count = " + localCount + " | Name Thread = " + Thread.currentThread().getName());
        }

        ExecutorService.increment(localCount);;

        return localCount ;
    }
}
