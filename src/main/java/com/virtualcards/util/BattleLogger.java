package com.virtualcards.util;

import java.util.ArrayList;
import java.util.List;

public class BattleLogger {

    private final List<String> events = new ArrayList<>();

    public void logOpponentGenerated(String name, int stage) {
        events.add("Opponent card generated: " + name + " (Evolution stage " + stage + ")");
    }

    public void logStart(String starterName) {
        events.add("Battle starts! " + starterName + " goes first.");
    }

    public void logAttack(String attacker, String target, int targetHealth) {
        events.add(attacker + " attacks: " + target + "'s health = " + targetHealth);
    }

    public void logWin(String name) {
        events.add(name + " won!");
    }

    public void logLoss(String name) {
        events.add(name + " lost!");
    }

    public void logXpGain(String name, int xpGained) {
        events.add(name + " won " + xpGained + " xp!");
    }

    public List<String> getEvents() {
        return events;
    }

}