package vn.vnpt.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ScheduleTaskConfiguration {

    @Bean
    public ScheduledExecutorService taskScheduler() {
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger counter = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("shared-scheduler-" + counter.getAndIncrement());
                t.setDaemon(true);  //shutdown app không cần watting
                return t;
            }
        };
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, threadFactory);
        // Khi task bị cancel → remove khỏi queue
        executor.setRemoveOnCancelPolicy(true);
        // Không tiếp tục task cũ khi shutdown
        executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        return executor;
    }
}
