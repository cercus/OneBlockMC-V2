package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.ArrayList;
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

    @JsonIgnore
    public void addPlayer(String value) {
        if(this.players == null)
            this.players = new ArrayList<>();
        this.players.add(value);
    }

    @JsonIgnore
    public void removePlayer(String value) {
        this.players.remove(value);
    }

    @Override
    public String toString() {
        return "Members{" +
                "player=" + players +
                '}';
    }
}
