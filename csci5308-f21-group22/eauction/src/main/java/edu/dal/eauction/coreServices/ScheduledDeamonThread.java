package edu.dal.eauction.coreServices;

import edu.dal.eauction.bidingSystem.service.BidingService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ScheduledDeamonThread {

    private BidingService service;

    public ScheduledDeamonThread(BidingService service) {
        this.service = service;
    }

    public void createDeamon() {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
               service.processBid();
            }
        }, 0, 5, TimeUnit.MINUTES);
    }

    @PostConstruct
    public void setup() {
        createDeamon();
    }
}
