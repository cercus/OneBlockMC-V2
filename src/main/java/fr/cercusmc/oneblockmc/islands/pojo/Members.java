package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;


public class Members {

    @JsonProperty("players")
    private List<String> players;

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> value) {
        this.players = value;
    }

    @Override
    public String toString() {
        return "Members{" +
                "player=" + players +
                '}';
    }
}
