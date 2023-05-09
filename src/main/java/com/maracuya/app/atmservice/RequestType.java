package com.maracuya.app.atmservice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RequestType {

    STANDARD(40),
    PRIORITY(20),
    SIGNAL_LOW(30),
    FAILURE_RESTART(10);

    // Determines the order in which the request should be served. The lower number, the earlier it should be served.
    final int priorityOrder;
}
