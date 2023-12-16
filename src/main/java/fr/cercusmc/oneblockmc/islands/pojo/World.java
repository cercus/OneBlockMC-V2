package fr.cercusmc.oneblockmc.islands.pojo;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="world")
public class World {

    @XmlAttribute
    private Double x;

    @XmlAttribute
    private Double y;

    @XmlAttribute
    private Double z;


    @XmlElement(name = "world", required = true)
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
