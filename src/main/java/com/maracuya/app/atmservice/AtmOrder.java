package com.maracuya.app.atmservice;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

public record AtmOrder(@JsonValue List<AtmDetails> atms) {
}
