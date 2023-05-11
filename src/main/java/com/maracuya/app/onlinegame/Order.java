package com.maracuya.app.onlinegame;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order {

    @JsonValue
    private final List<Group> groups = new ArrayList<>();

    void addGroup(Group group) {
        groups.add(group);
    }
}
