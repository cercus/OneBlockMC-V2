package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;



public class Loc {

    @JsonProperty("location")
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location value) {
        this.location = value;
    }

    @Override
    public String toString() {
        return "Loc{" +
                "location=" + location +
                '}';
    }


}
