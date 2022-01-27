package com.example.springboot.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class TimeNow {

/*
    @Scheduled(fixedDelay = 60*1000)
    public void scheduleFixedDelayTask() throws InterruptedException {
        log.info("Task1 - " + new Date());
    }
    @Scheduled(initialDelay = 1000,fixedRate = 60*1000)
    public void scheduleFixedRateTask() throws InterruptedException{
        log.info(("Task2 - "+ new Date()));
    }
    @Scheduled(cron = "* 1 * * * *")
    public void scheduleCronTask() throws InterruptedException{
        log.info(("Task3 - "+ new Date()));
    }
    */

}
