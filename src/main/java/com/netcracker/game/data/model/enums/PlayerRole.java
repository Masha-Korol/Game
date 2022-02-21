package com.netcracker.game.data.model.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum PlayerRole {
    TRAITOR,
    CIVILIAN;

    public static List<PlayerRole> randomRoles(int n) {
        Random random = new Random();
        boolean isTraitor = false;

        List<PlayerRole> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (random.nextFloat() > 0.5f && !isTraitor) {
                result.add(PlayerRole.TRAITOR);
                isTraitor = true;
            } else {
                result.add(PlayerRole.CIVILIAN);
            }
        }
        if (result.stream().noneMatch(v -> (v == PlayerRole.TRAITOR))) {
            result.remove(0);
            result.add(PlayerRole.TRAITOR);
        }
        return result;
    }
}
