package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
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
        return "Bans{" +
                "player=" + players +
                '}';
    }
}
