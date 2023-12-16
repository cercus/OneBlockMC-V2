package fr.cercusmc.oneblockmc.islands.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "center")
public class Loc {

    @XmlElement(required = true)
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
