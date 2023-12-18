package fr.cercusmc.oneblockmc.islands.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;

public class World {


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
        return "World{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", name='" + name + '\'' +
                '}';
    }
}
