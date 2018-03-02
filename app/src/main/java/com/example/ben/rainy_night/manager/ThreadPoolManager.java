package com.example.ben.rainy_night.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Ben
 * @date 2018/2/28
 */

public class ThreadPoolManager {

    private static volatile ThreadPool mPool = null;

    public static ThreadPool getInstance() {
        if (mPool == null) {
            synchronized (ThreadPool.class) {
                if (mPool == null) {
                    mPool = new ThreadPool();
                }
            }
        }
        return mPool;
    }


    public static class ThreadPool {
        /**
         * 核心线程池的数量，同时能够执行的线程数量
         */
        private int corePoolSize;
        /**
         * 最大线程池数量，表示当缓冲队列满的时候能继续容纳的等待任务的数量
         */
        private int maximumPoolSize;
        /**
         * 线程执行时的休眠时间的数值
         */
        private long keepAliveTime = 1000;
        /**
         * 线程执行时休眠的时间的单位，也就是上一个参数的单位
         */
        private TimeUnit unit = TimeUnit.SECONDS;

        private ThreadPoolExecutor mExecutor = null;

        public ThreadPool() {
            this.corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
            this.maximumPoolSize = corePoolSize;
        }

        private void initExecutor() {
            mExecutor = new ThreadPoolExecutor(
                    corePoolSize, maximumPoolSize, keepAliveTime, unit,
                    new LinkedBlockingDeque<Runnable>(),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());
        }

        public void execute(Runnable r) {
            if (mExecutor == null || mExecutor.isShutdown()) {
                initExecutor();
            }
            mExecutor.execute(r);
        }

        public void cancel() {
            if (mExecutor != null) {
                mExecutor.shutdown();
            }
        }

    }


}
