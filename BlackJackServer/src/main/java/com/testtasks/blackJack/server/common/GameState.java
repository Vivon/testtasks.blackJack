package com.testtasks.blackJack.server.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GameState {

    D("deal", 'D'),
    S("stand", 'S'),
    E("end", 'E'),
    F("finish", 'F');

    private String stateAlias;
    private Character stateDBAlias;

    private GameState(String stateAlias, Character stateDBAlias) {
        this.stateAlias = stateAlias;
        this.stateDBAlias = stateDBAlias;
    }

    public Character getStateDBAlias() {
        return stateDBAlias;
    }

    @JsonValue
    public String jsonValue(){
        return this.stateAlias;
    }

    public static GameState valueOfDBAlias(Character alias) {
        for (GameState gameState : GameState.values()) {
            if (gameState.getStateDBAlias().equals(alias)){
                return gameState;
            }
        }
        throw new IllegalArgumentException("Not valid Game state in DB!!");
    }
}
