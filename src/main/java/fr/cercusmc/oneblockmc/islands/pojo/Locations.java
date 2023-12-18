package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;



public class Locations {


    @JsonProperty("center")
    private Loc center;


    @JsonProperty("home")
    private Loc home;


    @JsonProperty("spawn")
    private Loc spawn;


    @JsonProperty("warp")
    private Loc warp;

    public Loc getCenter() {
        return center;
    }

    public void setCenter(Loc value) {
        this.center = value;
    }

    public Loc getHome() {
        return home;
    }

    public void setHome(Loc value) {
        this.home = value;
    }

    public Loc getSpawn() {
        return spawn;
    }

    public void setSpawn(Loc value) {
        this.spawn = value;
    }

    public Loc getWarp() {
        return warp;
    }

    public void setWarp(Loc value) {
        this.warp = value;
    }

    @Override
    public String toString() {
        return "Locations{" +
                "center=" + center +
                ", home=" + home +
                ", spawn=" + spawn +
                ", warp=" + warp +
                '}';
    }
}
