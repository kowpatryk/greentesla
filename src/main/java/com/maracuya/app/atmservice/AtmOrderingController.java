package com.maracuya.app.atmservice;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
public class AtmOrderingController {

    private final AtmOrderingService orderingService;

    public AtmOrderingController(AtmOrderingService orderingService) {
        this.orderingService = orderingService;
    }

    @PostMapping("/atms/calculateOrder")
    public List<AtmDetails> calculate(@RequestBody @Valid List<ServiceTask> serviceTasks) {
        return orderingService.calculateOrder(serviceTasks);
    }
}
