package com.maracuya.app.onlinegame;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.function.Consumer;

import static java.util.Comparator.comparing;

@Service
public class OnlineGameOrderingService {

    private static final Comparator<Clan> CLAN_PRIORITY_COMPARATOR =
        comparing(Clan::points).reversed().thenComparing(Clan::numberOfPlayers);

    public Order calculateOrder(Players players) {
        int maxPlayersInGroup = players.groupCount();

        Order order = new Order();
        players.clans().stream()
            .sorted(CLAN_PRIORITY_COMPARATOR)
            .forEach(processClan(order, maxPlayersInGroup));

        return order;
    }

    private Consumer<Clan> processClan(Order order, int maxPlayersInGroup) {
        return clan -> order.getGroups().stream()
            .filter(group -> clan.numberOfPlayers() <= maxPlayersInGroup - group.size)
            .findFirst()
            .ifPresentOrElse(
                group -> group.addClan(clan),
                () -> order.addGroup(new Group().addClan(clan))
            );
    }
}
