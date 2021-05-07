package com.covidstat.schedule;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:quartz.properties")
public class QuartzCfg {

    @Value("${org.quartz.cron.scheduler}")
    private String cronSchedule;

    @Value("${org.quartz.scheduler.job.identity}")
    private String jobName;

    @Value("${org.quartz.scheduler.trigger.identity}")
    private String triggerName;

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(Updater.class)
                .storeDurably()
                .withIdentity(jobName)
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity(triggerName)
                .withSchedule(cronSchedule(cronSchedule))
                .build();
    }
}
