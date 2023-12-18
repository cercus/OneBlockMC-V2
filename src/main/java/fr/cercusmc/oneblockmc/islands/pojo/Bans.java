package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Bans {

    public Bans() {

    }


    @JsonProperty("players")
    private List<String> players;

    public void setPlayers(List<String> value) {
        this.players = value;
    }

    public List<String> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return "Bans{" +
                "player=" + players +
                '}';
    }
}
