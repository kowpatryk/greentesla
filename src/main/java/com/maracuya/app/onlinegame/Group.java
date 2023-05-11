package com.maracuya.app.onlinegame;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Group {

    @JsonValue
    private final List<Clan> clans = new ArrayList<>();

    @JsonIgnore
    int size;

    Group addClan(Clan clan) {
        clans.add(clan);
        size += clan.numberOfPlayers();
        return this;
    }
}
