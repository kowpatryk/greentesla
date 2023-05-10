package com.maracuya.app.atmservice;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ServiceTask(

    @Min(1)
    @Max(9999)
    int region,

    RequestType requestType,

    @Min(1)
    @Max(9999)
    int atmId
) {
}
