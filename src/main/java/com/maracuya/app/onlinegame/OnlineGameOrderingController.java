package com.maracuya.app.onlinegame;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class OnlineGameOrderingController {

    private final OnlineGameOrderingService gameOrderingService;

    @PostMapping("/onlinegame/calculate")
    public Order calculate(@RequestBody @Valid Players players) {
        return gameOrderingService.calculateOrder(players);
    }
}
