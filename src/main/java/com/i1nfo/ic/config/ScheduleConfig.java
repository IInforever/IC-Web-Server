/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.config;

import com.i1nfo.ic.service.PasteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
@Slf4j
public class ScheduleConfig {

    private final PasteService pasteService;

    @Autowired
    public ScheduleConfig(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @Scheduled(fixedDelay = 7, timeUnit = TimeUnit.HOURS)
    public void doClean() {
        int c = pasteService.cleanUpExpiredPaste();
        log.info(String.format("Auto clean up: %d expired records deleted", c));
    }

}
