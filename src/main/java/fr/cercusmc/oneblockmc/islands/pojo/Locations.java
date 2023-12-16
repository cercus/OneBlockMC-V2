package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "locations")
public class Locations {

    @XmlElement(required = true)
    @JsonProperty("center")
    private Loc center;

    @XmlElement(required = true)
    @JsonProperty("home")
    private Loc home;

    @XmlElement(required = true)
    @JsonProperty("spawn")
    private Loc spawn;

    @XmlElement(required = true)
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
