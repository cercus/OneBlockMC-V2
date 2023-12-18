package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Stats {


    @JsonProperty("level")
    private Double level;


    @JsonProperty("nbBlock")
    private Integer nbBlock;


    @JsonProperty("phase")
    private Integer phase;


    @JsonProperty("radius")
    private Integer radius;

    public Double getLevel() {
        return level;
    }

    public void setLevel(Double value) {
        this.level = value;
    }

    public Integer getNbBlock() {
        return nbBlock;
    }

    public void setNbBlock(Integer value) {
        this.nbBlock = value;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer value) {
        this.phase = value;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer value) {
        this.radius = value;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "level=" + level +
                ", nbBlock=" + nbBlock +
                ", phase=" + phase +
                ", radius=" + radius +
                '}';
    }
}
