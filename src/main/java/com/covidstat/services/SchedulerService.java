package com.covidstat.services;

import javax.annotation.PreDestroy;
import javax.persistence.PrePersist;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;

public class SchedulerService {

    private final Scheduler scheduler;
    private final JobDetail jobDetail;
    private final Trigger trigger;

    @Autowired
    public SchedulerService(Scheduler scheduler, JobDetail jobDetail, Trigger trigger) {
        this.scheduler = scheduler;
        this.jobDetail = jobDetail;
        this.trigger = trigger;
    }

    @PrePersist
    public void init() throws SchedulerException {
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @PreDestroy
    public void desctroy() throws SchedulerException {
        scheduler.shutdown();
    }

}
