package com.uniware.hackathonpractice.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.uniware.hackathonpractice.user.service.ConfirmationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.uniware.hackathonpractice.scheduler.TimeConstants.ONE_DAY_IN_MILLIS;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    public ScheduledTasks(ConfirmationTokenService confirmationTokenService) {
        this.confirmationTokenService = confirmationTokenService;
    }

    @Scheduled(fixedRate = ONE_DAY_IN_MILLIS)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        confirmationTokenService.deleteAllExpired();
    }
}
