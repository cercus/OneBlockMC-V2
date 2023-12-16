package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "stats")
public class Stats {

    @XmlElement
    @JsonProperty("level")
    private Double level;

    @XmlElement
    @JsonProperty("nbBlock")
    private Integer nbBlock;

    @XmlElement
    @JsonProperty("phase")
    private Integer phase;

    @XmlElement
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
