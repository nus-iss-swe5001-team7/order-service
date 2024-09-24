package com.nus.edu.se.groupfoodorder.service.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeController {

    private final TimerService timerService;

    @Autowired
    public TimeController(TimerService timerService) {
        this.timerService = timerService;
    }

    @GetMapping("/timer")
    public Integer getTimerValue(@RequestParam(value = "parameter") String groupOrderId) {
        return timerService.getOrderTimers().getOrDefault(groupOrderId, 0);
    }

}
