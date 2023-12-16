package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "location")
public class Location {

    @XmlElement
    @JsonProperty("x")
    private Double x;

    @XmlElement
    @JsonProperty("y")
    private Double y;

    @XmlElement
    @JsonProperty("z")
    private Double z;


    @XmlElement
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


}
