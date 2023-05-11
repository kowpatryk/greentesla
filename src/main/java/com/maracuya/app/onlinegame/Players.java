package com.maracuya.app.onlinegame;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;

public record Players(

    @Min(1)
    @Max(1000)
    int groupCount,

    @Size(max = 20000)
    List<Clan> clans
) {
}
