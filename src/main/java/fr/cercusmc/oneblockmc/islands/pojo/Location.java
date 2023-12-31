package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bukkit.Bukkit;


public class Location {


    @JsonProperty("x")
    private Double x;

    @JsonProperty("y")
    private Double y;

    @JsonProperty("z")
    private Double z;

    @JsonProperty("name")
    private String name;

    public Double getX() {
        return x;
    }

    public void setX(Double value) {
        this.x = value;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double value) {
        this.y = value;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double value) {
        this.z = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", name='" + name + '\'' +
                '}';
    }

    @JsonIgnore
    public org.bukkit.Location toLocation() {
        return new org.bukkit.Location(Bukkit.getWorld(this.name), this.x, this.y, this.z);
    }


}
